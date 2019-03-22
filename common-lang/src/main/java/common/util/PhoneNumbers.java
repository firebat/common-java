package common.util;

import com.google.common.base.Preconditions;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class PhoneNumbers {

    public static final String UNKNOWN_REGION = "ZZ";

    /**
     * 获取号码所属的国家/地区
     * 例如国家码同样是+1，如果后续regioncode是514的，那么regioncode不是US，而是CA
     * 如果regioncode不存在，返回"ZZ"
     * <b>该方法未做号码的合法性检查</b>
     *
     * @param phone 必须是包含国家码的号码，如8615298765432
     * @return
     */
    public static String getRegionCode(String phone) {
        try {
            Phonenumber.PhoneNumber number = parse(phone);
            return getRegionCode(number);
        } catch (NumberParseException e) {
            return UNKNOWN_REGION;
        }
    }

    /**
     * 获取号码所属的国家/地区
     * 例如国家码同样是+1，如果后续regioncode是514的，那么regioncode不是US，而是CA
     * 如果regioncode不存在，返回null
     * <b>该方法未做号码的合法性检查</b>
     * 如果是'001'返回null
     */
    public static String getRegionCode(Phonenumber.PhoneNumber number) {

        Preconditions.checkNotNull(number);

        String regionCode = PhoneNumberUtil.getInstance().getRegionCodeForNumber(number);

        // 当从号码无法拿到regioncode，尝试只从国家码获取regioncode，主要为公众号服务
        if ((regionCode == null || UNKNOWN_REGION.equals(regionCode) || "001".equals(regionCode))
                && number.getCountryCode() > 0) {
            regionCode = PhoneNumberUtil.getInstance().getRegionCodeForCountryCode(number.getCountryCode());
        }

        return UNKNOWN_REGION.equals(regionCode) || "001".equals(regionCode) ? null : regionCode;
    }

    public static Phonenumber.PhoneNumber parse(String phone) throws NumberParseException {
        Preconditions.checkArgument(phone != null && !phone.isEmpty(), "phone number required.");
        String numberToParse = phone.startsWith("+") ? phone : "+" + phone;

        return fix(PhoneNumberUtil.getInstance().parse(numberToParse, null));
    }

    /**
     * 针对一些特殊情况做特殊处理
     * 比如墨西哥，由于+529621828737和+5219621828737实际属于同一个号码，故统一处理为+5219621828737
     * 比如阿根廷，由于+543425931504和+5493425931504实际属于同一个号码，故统一处理为+5493425931504
     */
    private static Phonenumber.PhoneNumber fix(Phonenumber.PhoneNumber number) {

        Preconditions.checkNotNull(number);

        long national = number.getNationalNumber();
        if (number.getCountryCode() == 52 && String.valueOf(national).length() == 10) {
            number.setNationalNumber(Long.valueOf("1" + national));
        } else if (number.getCountryCode() == 54 && String.valueOf(national).length() == 10) {
            number.setNationalNumber(Long.valueOf("9" + national));
        }

        return number;
    }
}
