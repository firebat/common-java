package common.web.annotation;

import common.web.spring.handler.JsonBodyImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(JsonBodyImportSelector.class)
public @interface EnableJsonBody {

    String code() default "code";

    String data() default "data";

    String message() default "message";
}
