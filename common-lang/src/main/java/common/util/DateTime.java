package common.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 数字时间
 */
public class DateTime {

    private final long longValue;

    @JsonCreator
    public DateTime(@JsonProperty("longValue") long longValue) {
        this.longValue = longValue;
    }

    public long getLongValue() {
        return longValue;
    }
}
