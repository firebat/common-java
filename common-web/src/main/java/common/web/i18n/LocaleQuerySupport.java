package common.web.i18n;

import common.api.query.GenericQuery;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public abstract class LocaleQuerySupport implements LocaleSupport {

    private static final ThreadLocal<Locale> locale = new ThreadLocal<>();

    public static void setLocale(Locale value) {
        locale.set(value);
    }

    @Override
    public Locale getLocale() {

        Locale value = locale.get();

        if (value != null) {
            // 手工设定优先
            return value;
        }

        // 客户端设置
        HttpServletRequest request = getRequest();

        // 服务器设置
        return request == null ? null : request.getLocale();
    }

    protected String getLanguage() {
        Locale locale = getLocale();
        return locale == null ? null : locale.getLanguage();
    }

    protected GenericQuery createQuery() {
        return new GenericQuery().fill("lang", getLanguage());
    }

    protected GenericQuery createQuery(Integer page, Integer limit) {
        if (page == null || page < 0) {
            page = getDefaultPage();
        }

        if (limit == null) {
            limit = getDefaultPageSize();
        }

        int maxPageSize = getMaxPageSize();
        if (limit > maxPageSize) {
            limit = maxPageSize;
        }

        Integer offset = (page - 1) * limit;
        return createQuery().limit(limit).offset(offset);
    }

    protected int getDefaultPage() {
        return 1;
    }

    protected int getDefaultPageSize() {
        return 30;
    }

    protected int getMaxPageSize() {
        return Integer.MAX_VALUE;
    }

    protected HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return requestAttributes == null ? null : ((ServletRequestAttributes) requestAttributes).getRequest();
    }
}
