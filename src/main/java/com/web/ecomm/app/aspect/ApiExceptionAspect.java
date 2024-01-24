package com.web.ecomm.app.aspect;

import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.ThrowsAdvice;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

@Slf4j
@Aspect
public class ApiExceptionAspect implements ThrowsAdvice {

    @AfterThrowing(pointcut = "execution(* com.web.ecomm.app.service.*.*(..))", throwing = "ex")
    public void afterThrowingService(JoinPoint joinPoint, Throwable ex)
            throws BusinessException, SystemException {

        log.error("Exception : " + ex);

        if (ex instanceof KeyManagementException ||
                ex instanceof NoSuchAlgorithmException ||
                ex instanceof KeyStoreException ||
                ex instanceof CertificateException ||
                ex instanceof IOException ||
                ex instanceof UnrecoverableKeyException) {

            throw new SystemException(ex.getMessage(), Constants.ERR_DEFAULT);

        } else if(ex instanceof BusinessException) {
            throw (BusinessException) ex;

        }else if(ex instanceof SystemException) {
            throw (SystemException) ex;

        } else {
            throw new SystemException(ex.getMessage(), Constants.ERR_DEFAULT);

        }

    }

    @AfterThrowing(pointcut = "execution(* com.web.ecomm.app.dao.*.*(..))", throwing = "ex")
    public void afterThrowingDao(JoinPoint joinPoint, Throwable ex)
            throws BusinessException, SystemException {

        log.error("Exception : " + ex);

        if (ex instanceof SystemException) {
            throw new SystemException(ex.getMessage(), Constants.ERR_DEFAULT);

        } else if(ex instanceof BusinessException) {
            throw (BusinessException) ex;

        } else {
            throw new SystemException(ex.getMessage(), Constants.ERR_DEFAULT);
        }
    }
}
