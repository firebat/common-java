package common.web.spring.handler;

import common.web.annotation.JsonBody;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JsonBodyMethodProcessor implements HandlerMethodReturnValueHandler {

    public boolean supportsReturnType(MethodParameter returnValue) {
        return returnValue.getMethodAnnotation(JsonBody.class) != null;
    }

    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {

        mavContainer.setRequestHandled(true);

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);

        JsonSerializer.write(returnValue, returnType.getMethod(), request, response);
    }
}
