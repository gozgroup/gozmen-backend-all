package com.gozmen.ucenter.controller;

import com.gozmen.common.annotations.Authentication;
import com.gozmen.ucenter.service.AuthenticationService;
import com.gozmen.ucenter.utils.Constants;
import com.gozmen.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //如果验证token失败，并且方法注明了Authorization，返回401错误
        final Authentication authentication = method.getAnnotation(Authentication.class);
        if (authentication != null && authentication.token()) {
            LOGGER.warn(request.getRequestURI() + " need to check token");
            //从header中得到token
            String tokenValue = request.getHeader(Constants.HEADER_TOKEN);

            String username = authenticationService.getUsernameBy(tokenValue);
            if (!StringUtils.isEmpty(username) && Objects.equals(username, request.getHeader(Constants.HEADER_USERNAME))) {
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        }
        return true;
    }
}
