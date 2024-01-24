package com.web.ecomm.app.config;

import com.web.ecomm.app.dto.APIResponseEntity;
import com.web.ecomm.app.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@ControllerAdvice
public class APIResponseInterceptor implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        String reqId = request.getHeaders().getFirst(Constants.REQ_ID_KEY);

        if (body instanceof APIResponseEntity<?>) {
            APIResponseEntity responseEntity = (APIResponseEntity) body;

            String code = responseEntity.getCode();
            String status = responseEntity.getStatus();

            if (StringUtils.equals(status, Constants.STATUS_SUCCESS) && StringUtils.equals(code, Constants.SUCCESS_CODE)) {
                if (StringUtils.isBlank(reqId)) {
                    reqId = MDC.get(Constants.REQ_ID_KEY);
                }

                if (!StringUtils.isBlank(reqId)) {
                    responseEntity.setReqId(reqId);
                }
                responseEntity.setMessage(Constants.STATUS_SUCCESS);
                responseEntity.setCode(Constants.SUCCESS_CODE);
                body = responseEntity;
            }
        }

        String requestStartTime = MDC.get(Constants.REQUEST_START_TIME);
        if (StringUtils.isNumeric(requestStartTime)) {
            String requestURI = request.getURI().toString();
            long startTime = Long.valueOf(requestStartTime);
            long endTime = System.currentTimeMillis();
            log.info("URI: {}, Total Request Time: {}, (ms)", requestURI, endTime - startTime);
        }

        MDC.clear();
        return body;
    }
}
