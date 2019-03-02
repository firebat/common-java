package common.web.spring.handler;

import common.web.annotation.JsonBody;
import org.springframework.core.Ordered;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.mvc.annotation.ModelAndViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


public class JsonBodyExceptionResolver extends SimpleMappingExceptionResolver {

    public JsonBodyExceptionResolver() {
        this.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response,
                                              Object _handler, Exception ex) {

        if (_handler == null || !(_handler instanceof HandlerMethod)) {
            // like 'GET' not supported
            return null;
        }

        HandlerMethod handler = (HandlerMethod) _handler;
        Method method = handler.getMethod();

        if (method.isAnnotationPresent(JsonBody.class)) {

            JsonSerializer.write(ex, method, request, response);

            // skip other resolver and view render
            return ModelAndViewResolver.UNRESOLVED;
        }

        return null;
    }
}