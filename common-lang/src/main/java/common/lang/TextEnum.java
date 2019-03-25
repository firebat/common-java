package common.lang;

import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;

public interface TextEnum {

    String text();

    static <T extends TextEnum> Map<T, String> toMap(T[] values) {
        Map map = Maps.newLinkedHashMapWithExpectedSize(values.length);
        for (T value : values) {
            map.put(value, value.text());
        }
        return Collections.unmodifiableMap(map);
    }
}
