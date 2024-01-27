package com.web.ecomm.app.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@ToString
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class APIResponseEntity<T> {

    @JsonProperty("status")
    private String status;

    @JsonProperty("data")
    private T data;

    @JsonProperty("message")
    private String message;

    @JsonProperty("code")
    private String code;

    @JsonProperty("reqId")
    private String reqId;

    public APIResponseEntity(String status, String code, T result) {
        this.status = status;
        this.code = code;
        this.data = result;
    }

    public APIResponseEntity(String status, String message, String code, String reqId) {
        this.status = status;
        this.message = message;
        this.code = code;
        this.reqId = reqId;
    }
}
