package com.web.ecomm.app.config;

import com.web.ecomm.app.utils.Constants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.security.SecureRandom;
import java.util.Enumeration;

@Slf4j
public class APIRequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.clear();

        long start = System.currentTimeMillis();
        MDC.put(Constants.REQUEST_START_TIME, Long.toString(start));

        log.debug("Interceptor Started at {}", start);
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames != null && headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            log.debug("Request Header Name: {}, Value: {}", name, request.getHeader(name));
        }

        String reqId = request.getHeader(Constants.REQ_ID_KEY);
        if (!StringUtils.isEmpty(reqId)) {
            log.info("Received API Request ReqId: {}", reqId);
        } else {
            reqId = "REQ" + String.valueOf(System.currentTimeMillis()) +
                    Math.abs(request.getRequestURI().concat(String.valueOf(new SecureRandom().nextInt())).hashCode());
            log.info("Generated API Request ReqId: {}", reqId);
        }

        MDC.put(Constants.REQ_ID_KEY, reqId);
        log.info("Request interceptor took {} (ms) time", System.currentTimeMillis() - start);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear();
    }
}
