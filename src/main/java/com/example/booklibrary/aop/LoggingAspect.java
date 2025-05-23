package com.example.booklibrary.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    // 컨트롤러의 모든 메서드를 대상으로 하는 포인트컷
    @Pointcut("execution(* com.example.booklibrary.controller.*.*(..))")
    private void controllerPointcut() {}
    
    // 서비스의 모든 메서드를 대상으로 하는 포인트컷
    @Pointcut("execution(* com.example.booklibrary.service.*.*(..))")
    private void servicePointcut() {}
    
    // 리포지토리의 모든 메서드를 대상으로 하는 포인트컷
    @Pointcut("execution(* com.example.booklibrary.repository.*.*(..))")
    private void repositoryPointcut() {}
    
    // 모든 계층의 포인트컷을 조합
    @Pointcut("controllerPointcut() || servicePointcut() || repositoryPointcut()")
    private void applicationPointcut() {}
    
    // 메서드 실행 전후 로깅을 위한 어드바이스
    @Around("applicationPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isDebugEnabled()) {
            log.debug("Enter: {}.{}() with argument[s] = {}", 
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), 
                    Arrays.toString(joinPoint.getArgs()));
        }
        
        try {
            Object result = joinPoint.proceed();
            if (log.isDebugEnabled()) {
                log.debug("Exit: {}.{}() with result = {}", 
                        joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(), 
                        result);
            }
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", 
                    Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName());
            throw e;
        }
    }
    
    // 예외 발생 시 로깅을 위한 어드바이스
    @AfterThrowing(pointcut = "applicationPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in {}.{}() with cause = {}", 
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), 
                e.getCause() != null ? e.getCause() : "NULL");
    }
}