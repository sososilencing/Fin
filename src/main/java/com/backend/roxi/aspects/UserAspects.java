package com.backend.roxi.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author Roxi酱
 */
@Aspect
@Component
public class UserAspects {
    @Pointcut("execution(public * com.backend.roxi.controller.UserController.*(..))")
    public void cut(){

    }

    @Around("cut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        return pjp.proceed();
    }
}
