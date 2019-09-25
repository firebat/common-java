package common.web.spring.handler;

import common.api.json.CodeMessage;
import common.web.annotation.JsonBody;
import org.springframework.core.Ordered;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.annotation.ModelAndViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;

public class JsonBodyExceptionResolver extends AbstractHandlerExceptionResolver {

    private Map<Class<? extends Exception>, CodeMessage> exceptionMapping = Collections.emptyMap();

    public JsonBodyExceptionResolver() {
        this.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response,
                                              Object _handler, Exception exception) {

        if (!(_handler instanceof HandlerMethod)) {
            // like 'GET' not supported
            return null;
        }

        HandlerMethod handler = (HandlerMethod) _handler;
        Method method = handler.getMethod();

        JsonBody meta = method.getAnnotation(JsonBody.class);
        if (meta == null) {
            return null;
        }

        if (meta.logException()) {
            logger.error(exception.getMessage(), exception);
        }

        Object mapped = getMappedException(exception);
        JsonSerializer.write(mapped == null ? exception : mapped, meta, request, response);
        // skip other resolver and view render
        return ModelAndViewResolver.UNRESOLVED;
    }

    private Object getMappedException(Exception exception) {

        for (Map.Entry<Class<? extends Exception>, CodeMessage> entry : exceptionMapping.entrySet()) {
            if (exception.getClass().isAssignableFrom(entry.getKey())) {
                return entry.getValue();
            }
        }

        return null;
    }

    public void setExceptionMapping(Map<Class<? extends Exception>, CodeMessage> exceptionMapping) {
        this.exceptionMapping = exceptionMapping;
    }
}