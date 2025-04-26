package com.example.booklibrary.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class PerformanceAspect {
    
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    // 서비스 메서드 실행 시간 측정
    @Around("execution(* com.example.booklibrary.service.*.*(..))")
    public Object measureServiceExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        Object result = joinPoint.proceed();
        
        stopWatch.stop();
        log.info("메서드 {} 실행 시간: {}ms", 
                joinPoint.getSignature().toShortString(), 
                stopWatch.getTotalTimeMillis());
        
        return result;
    }
}