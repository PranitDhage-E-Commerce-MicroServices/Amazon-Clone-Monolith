package com.app.config;

import com.app.dto.ResponseDTO;
import com.app.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
public class APIResponseInterceptor implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        String reqId = request.getHeaders().getFirst(Constants.REQ_ID_KEY);

        if (body instanceof ResponseDTO<?>) {
            ResponseDTO responseDTO = (ResponseDTO) body;
            if (StringUtils.isBlank(reqId)) {
                reqId = MDC.get(Constants.REQ_ID_KEY);
            }

            if (!StringUtils.isBlank(reqId)) {
                log.info("Returning API Response ReqId: {}", reqId);
                responseDTO.setReqId(reqId);
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
