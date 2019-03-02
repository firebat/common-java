package common.rpc.url;

import junit.framework.TestCase;
import org.junit.Test;

public class AuthUrlTest extends TestCase {

    @Test
    public void testUrl() {
        AuthUrl url = new AuthUrl("http://www.exmaple.com/hello", "hello-app", "123456");

        System.out.println(url.build());
        System.out.println(url.build());
        System.out.println(url.build());
        System.out.println(url.build());
        System.out.println(url.build());
    }
}
