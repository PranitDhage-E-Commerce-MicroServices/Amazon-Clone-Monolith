package com.web.ecomm.app.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
@Slf4j
public class ApiLoggingAspect {

    @Pointcut("execution(* com.web.ecomm.app.service.*.*(..))" +
            "&& !@target(com.web.ecomm.app.utils.NoLoggingMarker)")
    public void logForAllMethods() {

    }

    @Around("logForAllMethods()")
    public static Object logAroundAllMethods(ProceedingJoinPoint pjp) throws Throwable {

        // Retrieve the Method parameters types (static)
        final Signature signature = pjp.getStaticPart().getSignature();
        String methodName = pjp.getSignature().getName();
        String packageName = "";
        String targetClass = "";
        String paramName = "";

        if (signature instanceof MethodSignature) {
            final MethodSignature ms = (MethodSignature) signature;
            packageName = signature.getDeclaringTypeName();
            targetClass = pjp.getTarget().getClass().getSimpleName();

            String [] paramNames = ms.getParameterNames();

            for (String s : paramNames) {
                paramName = s + "|" + paramName;
            }
        }

        //Retrieve Runtime Method Arguments
        String paramValue = "";
        for (final Object argument : pjp.getArgs()) {
            paramValue = argument + "|" + paramValue;
        }

        long start = System.currentTimeMillis();

        Object output = pjp.proceed();

        long elapsedTime = System.currentTimeMillis() - start;

        log.info("Total time elapsed for {}, {}, {} with parameters {}, {} is {} (ms) time", packageName, targetClass, methodName, paramName, paramValue, elapsedTime);

        return output;
    }
}
