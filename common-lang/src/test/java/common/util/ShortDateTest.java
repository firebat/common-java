package common.util;

import junit.framework.TestCase;
import org.junit.Test;

public class ShortDateTest extends TestCase {

    @Test
    public void testDate() {
        ShortDate date = new ShortDate(20191010);
        assertEquals("2019-10-10", date.toString());
    }
}
