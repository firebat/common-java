package common.web.configuration;

import common.web.spring.handler.JsonBodyExceptionResolver;
import common.web.spring.handler.JsonBodyMethodProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
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
        Method method = ReflectionUtils.findMethod(RequestMappingHandlerAdapter.class, "getDefaultReturnValueHandlers");
        ReflectionUtils.makeAccessible(method);
        List<HandlerMethodReturnValueHandler> returnValueHandlers = (List<HandlerMethodReturnValueHandler>) ReflectionUtils.invokeMethod(method, adapter);
        returnValueHandlers.add(0, new JsonBodyMethodProcessor());
        adapter.setReturnValueHandlers(returnValueHandlers);

        return adapter;
    }

    @Bean
    @ConditionalOnMissingBean
    public JsonBodyExceptionResolver jsonBodyExceptionResolver() {
        return new JsonBodyExceptionResolver();
    }
}
