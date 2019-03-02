package common.lang;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.Map;

public class CodeEnumTest extends TestCase {

    enum UserType implements CodeEnum {

        Manager(0),
        Employee(1);

        int code;

        UserType(int code) {
            this.code = code;
        }

        public int code() {
            return code;
        }

        private static Map<Integer, UserType> cache = CodeEnum.toMap(values());

        public static UserType codeOf(int code) {
            return cache.get(code);
        }
    }

    @Test
    public void testValue() {
        assertEquals(UserType.Manager, UserType.codeOf(0));
        assertEquals(UserType.Employee, UserType.codeOf(1));
    }
}
