package com.app.dto;

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
public class ResponseDTO<T> {
    private Boolean success;

    @JsonProperty("data")
    private T result;

    @JsonProperty("error")
    private String error;

    @JsonProperty("error")
    private String reqId;

    public ResponseDTO(Boolean success, T result) {
        this.success = success;
        this.result = result;
    }

    public ResponseDTO(Boolean success, String error, String result, String reqId) {
        this.success = success;
        this.error = error;
        this.result = (T) result;
        this.reqId = reqId;
    }

}
