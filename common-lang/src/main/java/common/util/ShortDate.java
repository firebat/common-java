package common.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 8位数字日期
 */
public class ShortDate implements Serializable, Comparable<ShortDate> {

    // yyyyMMdd
    private final int intValue;

    @JsonCreator
    public ShortDate(@JsonProperty("intValue") int intValue) {
        this.intValue = intValue;
    }

    @Override
    public int compareTo(ShortDate o) {
        return 0;
    }

    public int getIntValue() {
        return intValue;
    }

    @Override
    public int hashCode() {
        return intValue;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof ShortDate) && ((ShortDate) obj).intValue == intValue;
    }

    @Override
    public String toString() {
        return (intValue / 10000) + "-" + fillZero(intValue / 100 % 100) + "-" + fillZero(intValue % 100);
    }

    public boolean after(ShortDate date) {
        return intValue > date.intValue;
    }

    public boolean before(ShortDate date) {
        return intValue < date.intValue;
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
