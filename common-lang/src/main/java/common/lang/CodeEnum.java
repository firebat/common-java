package common.lang;


import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 自定义编码值枚举
 */
public interface CodeEnum {

    int code();

    static <T extends CodeEnum> Map<Integer, T> toMap(T[] values) {
        return Collections.unmodifiableMap(Arrays.stream(values).collect(Collectors.toMap(CodeEnum::code, Function.identity())));
    }
}
