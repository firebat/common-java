package common.id;

import org.junit.Test;

public class SnowFlakeTest {

    @Test
    public void testNext() {
        SnowFlake generator = new SnowFlake(2, 3);

        for (int i = 0; i < 100; i++) {
            System.out.println(generator.next());
        }
    }
}
