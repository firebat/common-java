package common.codec;

import junit.framework.TestCase;
import org.junit.Test;

public class LuhnTest extends TestCase {

    @Test
    public void testLuhn() {
        System.out.println(Luhn.generate("1234567890"));
        System.out.println(Luhn.validate("12345678903"));
    }
}
