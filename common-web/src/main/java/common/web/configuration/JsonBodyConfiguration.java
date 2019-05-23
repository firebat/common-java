package common.web.configuration;

import common.web.spring.handler.JsonBodyExceptionResolver;
import common.web.spring.handler.JsonBodyMethodProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 继承{@link DelegatingWebMvcConfiguration} 以注入 {@link JsonBodyMethodProcessor}
 */
@Configuration
public class JsonBodyConfiguration extends DelegatingWebMvcConfiguration {

    @Bean
    @Override
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {

        RequestMappingHandlerAdapter adapter = super.requestMappingHandlerAdapter();
        // JsonBodyMethodProcessor at first
        List<HandlerMethodReturnValueHandler> returnValueHandlers = getDefault(adapter, "getDefaultReturnValueHandlers");
        returnValueHandlers.add(0, new JsonBodyMethodProcessor());
        adapter.setReturnValueHandlers(returnValueHandlers);

        return adapter;
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> getDefault(RequestMappingHandlerAdapter adapter, String methodName) {
        try {
            Method method = RequestMappingHandlerAdapter.class.getDeclaredMethod(methodName);
            method.setAccessible(true);
            return (List<T>) method.invoke(adapter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public JsonBodyExceptionResolver jsonBodyExceptionResolver() {
        return new JsonBodyExceptionResolver();
    }
}
