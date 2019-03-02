package common.util;

import junit.framework.TestCase;

import java.math.BigDecimal;

public class MoneyTest extends TestCase {

    public void testStore() {

        assertEquals(BigDecimal.valueOf(0.111111), Money.of(0.1111114).storeDecimal());
        assertEquals(BigDecimal.valueOf(0.111112), Money.of(0.1111115).storeDecimal());
        assertEquals(BigDecimal.valueOf(0.111112), Money.of(0.1111116).storeDecimal());
        assertEquals(BigDecimal.valueOf(0.111112), Money.of(0.1111124).storeDecimal());
        assertEquals(BigDecimal.valueOf(0.111112), Money.of(0.1111125).storeDecimal());
        assertEquals(BigDecimal.valueOf(0.111113), Money.of(0.1111126).storeDecimal());

        assertEquals(111111, Money.of(0.1111114).storeLong());
        assertEquals(111112, Money.of(0.1111115).storeLong());
        assertEquals(111112, Money.of(0.1111116).storeLong());
        assertEquals(111112, Money.of(0.1111124).storeLong());
        assertEquals(111112, Money.of(0.1111125).storeLong());
        assertEquals(111113, Money.of(0.1111126).storeLong());
    }

    public void testCalculate() {

        assertTrue(Money.of(0.12345678).plus().compareTo(Money.of(0.12345678)) == 0);
        assertTrue(Money.of(0.12345678).plus(BigDecimal.valueOf(0.000000009)).compareTo(Money.of(0.1234567890)) == 0);
        assertTrue(Money.of(0.12345678).plus(Money.of(0.000000004), Money.of(0.000000005)).compareTo(Money.of(0.1234567890)) == 0);

        assertTrue(Money.of(0.123456789).minus().compareTo(Money.of(0.123456789)) == 0);
        assertTrue(Money.of(0.123456789).minus(BigDecimal.valueOf(0.000000009)).compareTo(Money.of(0.1234567800)) == 0);
        assertTrue(Money.of(0.123456789).minus(Money.of(0.000000001), Money.of(0.000000002), Money.of(0.000000003)).compareTo(Money.of(0.1234567830)) == 0);

        assertTrue(Money.of(0.12345678).multiply(1).compareTo(Money.of(0.12345678)) == 0);
        assertTrue(Money.of(0.12345678).multiply(1.0).compareTo(Money.of(0.12345678)) == 0);
        assertTrue(Money.of(0.00000001).multiply(2).compareTo(Money.of(0.00000002)) == 0);
        assertTrue(Money.of(0.00000001).multiply(2.0).compareTo(Money.of(0.00000002)) == 0);

        assertTrue(Money.of(0.12345678).divide(1).compareTo(Money.of(0.12345678)) == 0);
        assertTrue(Money.of(0.12345678).divide(1.0).compareTo(Money.of(0.12345678)) == 0);
        assertTrue(Money.of(0.00000002).divide(2).compareTo(Money.of(0.00000001)) == 0);
        assertTrue(Money.of(0.00000002).divide(2.0).compareTo(Money.of(0.00000001)) == 0);
    }

}
