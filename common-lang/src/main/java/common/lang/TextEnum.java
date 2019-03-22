package common.lang;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface TextEnum {

    String text();

    static <T extends TextEnum> Map<T, String> toMap(T[] values) {
        return Collections.unmodifiableMap(Arrays.stream(values).collect(Collectors.toMap(Function.identity(), TextEnum::text)));
    }
}
