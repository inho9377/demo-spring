package com.example.booklibrary.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CustomAnnotationAspect {
    
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Before("@annotation(com.example.booklibrary.annotation.Loggable)")
    public void logBeforeMethod(JoinPoint joinPoint) {
        log.info("커스텀 로깅: {} 메서드 실행", joinPoint.getSignature().toShortString());
    }
}