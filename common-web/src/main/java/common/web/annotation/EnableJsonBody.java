package common.web.annotation;

import common.web.configuration.JsonBodyConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(JsonBodyConfiguration.class)
public @interface EnableJsonBody {
}
