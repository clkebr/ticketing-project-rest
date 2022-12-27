package com.cydeo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@Slf4j
public class PerformanceAspect {

//    Logger log = LoggerFactory.getLogger(PerformanceAspect.class);

    @Pointcut("@annotation(com.cydeo.annotation.ExecutionTime)")
    private  void anyExecutionTimeOperation(){}

    @Around("anyExecutionTimeOperation()")
    public Object anyExecutionTimeOperationAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long beforeTime = System.currentTimeMillis();
        log.info("Execution will start");
        Object result;
        result = proceedingJoinPoint.proceed();
        long afterTime = System.currentTimeMillis();
        log.info("Time taken to execute: {} ms - Method: {} - Parameters: {}", (afterTime-beforeTime),
                proceedingJoinPoint.getSignature().toShortString(), proceedingJoinPoint.getArgs());
        return result;
    }
}
