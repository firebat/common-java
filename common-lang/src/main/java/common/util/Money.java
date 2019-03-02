package common.util;

import com.google.common.base.Preconditions;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;

/**
 * 账务金额运算，标准6位小数
 */
public class Money implements Comparable<Money>, Serializable {

    // 保留6位小数
    private static final transient int DEFAULT_SCALE = 6;
    // 末尾舍入方式 (1-4舍去, 6-9进位, 5向偶数靠拢)
    private static final transient RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_EVEN;
    // 运算精度
    private static final transient MathContext DEFAULT_MATH_CONTEXT = MathContext.DECIMAL64;

    private BigDecimal amount;

    private Money(BigDecimal amount) {
        this.amount = amount;
    }

    public static Money of(BigDecimal amount) {
        return new Money(amount);
    }

    public static Money of(double amount) {
        return of(BigDecimal.valueOf(amount));
    }

    public static Money of(long amount) {
        return of(BigDecimal.valueOf(amount));
    }

    public Money with(BigDecimal amount) {
        return this.amount.equals(amount) ? this : of(amount);
    }

    public static Money total(Money... monies) {
        Preconditions.checkArgument(monies.length > 0, "Money array required.");
        return Money.of(Arrays.stream(monies).map(Money::getAmount).reduce(BigDecimal.ZERO, (total, money) -> total.add(money, DEFAULT_MATH_CONTEXT)));
    }

    public Money plus(BigDecimal money) {
        return money.compareTo(BigDecimal.ZERO) == 0 ? this : with(amount.add(money, DEFAULT_MATH_CONTEXT));
    }

    public Money plus(Money... monies) {
        return with(Arrays.stream(monies).map(Money::getAmount).reduce(this.amount, (total, money) -> total.add(money, DEFAULT_MATH_CONTEXT)));
    }

    public Money minus(BigDecimal money) {
        return money.compareTo(BigDecimal.ZERO) == 0 ? this : with(amount.subtract(money, DEFAULT_MATH_CONTEXT));
    }

    public Money minus(Money... monies) {
        return with(Arrays.stream(monies).map(Money::getAmount).reduce(this.amount, (total, money) -> total.subtract(money, DEFAULT_MATH_CONTEXT)));
    }

    public Money multiply(double operand) {
        return operand == 1 ? this : with(amount.multiply(BigDecimal.valueOf(operand), DEFAULT_MATH_CONTEXT));
    }

    public Money multiply(long operand) {
        return operand == 1 ? this : with(amount.multiply(BigDecimal.valueOf(operand), DEFAULT_MATH_CONTEXT));
    }

    public Money divide(double operand) {
        return operand == 1 ? this : with(amount.divide(BigDecimal.valueOf(operand), DEFAULT_MATH_CONTEXT));
    }

    public Money divide(long operand) {
        return operand == 1 ? this : with(amount.divide(BigDecimal.valueOf(operand), DEFAULT_MATH_CONTEXT));
    }

    public Money negate() {
        return amount.compareTo(BigDecimal.ZERO) == 0 ? this : with(amount.negate());
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public long storeLong() {
        // 整型存储，移位小数
        return amount.movePointRight(DEFAULT_SCALE).setScale(0, DEFAULT_ROUNDING_MODE).longValue();
    }

    public BigDecimal storeDecimal() {
        // 数字存储，调整精度
        return amount.setScale(DEFAULT_SCALE, DEFAULT_ROUNDING_MODE);
    }

    @Override
    public int compareTo(Money o) {
        return amount.compareTo(o.amount);
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || (obj instanceof Money && amount.equals(((Money) obj).amount));
    }

    @Override
    public int hashCode() {
        return 7 * amount.hashCode();
    }

    @Override
    public String toString() {
        return amount == null ? "null" : amount.toString();
    }
}
