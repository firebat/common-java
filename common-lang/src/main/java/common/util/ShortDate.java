package common.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;
import java.util.Date;

/**
 * 8位数字日期
 */
public class ShortDate implements Serializable, Comparable<ShortDate> {

    // yyyyMMdd
    private final int value;

    @JsonCreator
    public ShortDate(int value) {
        this.value = value;
    }

    @JsonCreator
    public ShortDate(String value) {
        this(Integer.valueOf(value));
    }

    @Override
    public int compareTo(ShortDate o) {
        return value - o.value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof ShortDate) && ((ShortDate) obj).value == value;
    }

    @Override
    public String toString() {
        return (value / 10000) + "-" + fillZero(value / 100 % 100) + "-" + fillZero(value % 100);
    }

    public boolean after(ShortDate date) {
        return value > date.value;
    }

    public boolean before(ShortDate date) {
        return value < date.value;
    }

    public static ShortDate of(Date date) {
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int day = date.getDate();
        return new ShortDate(year * 10000 + month * 100 + day);
    }

    static String fillZero(int i) {
        return i < 10 ? "0" + i : Integer.toString(i);
    }
}
