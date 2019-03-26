package common.lang;

import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

public interface TextEnum {

    String text();

    static <T extends TextEnum> Map<T, String> toMap(T[] values) {
        return toMap(values, TextEnum::text);
    }

    static <T extends TextEnum> Map<T, String> toMap(T[] values, Function<TextEnum, String> function) {
        Map map = Maps.newLinkedHashMapWithExpectedSize(values.length);
        for (T value : values) {
            map.put(value, function.apply(value));
        }
        return Collections.unmodifiableMap(map);
    }
}
