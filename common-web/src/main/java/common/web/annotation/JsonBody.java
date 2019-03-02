package common.web.annotation;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonBody {

    /**
     * JsonP回调方法
     */
    String callback() default "callback";

    /**
     * 记录异常日志
     */
    boolean logException() default true;
}
