package common.util;

import junit.framework.TestCase;

public class PhoneNumbersTest extends TestCase {

    public void testGetRegionCode() {
        assertEquals("CN", PhoneNumbers.getRegionCode("8615811111111"));
        assertEquals("MX", PhoneNumbers.getRegionCode("529621828737"));
        assertEquals("MX", PhoneNumbers.getRegionCode("5219621828737"));
        assertEquals("AR", PhoneNumbers.getRegionCode("543425931504"));
        assertEquals("AR", PhoneNumbers.getRegionCode("5493425931504"));
    }
}
