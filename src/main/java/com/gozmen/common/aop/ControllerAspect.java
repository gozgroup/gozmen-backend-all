package com.gozmen.common.aop;

import com.gozmen.common.api.ApiUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);

    @Pointcut("execution(public * com.gozmen.ucenter.controller.*.*(..))")
    public void pointcut(){}

    //环绕通知,环绕增强，相当于MethodInterceptor
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) {
        LOGGER.info("start");
        try {
            Object returnValue = pjp.proceed();
            LOGGER.info("end");
            return returnValue;
        } catch (Throwable e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return ApiUtils.getErrorResponse(e);
        }
    }

}
