package com.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseDTO<T> {
    private Boolean success;

    @JsonProperty("data")
    private T result;

    @JsonProperty("error")
    private String error;

    public ResponseDTO() {
        System.out.println("in " + getClass().getName());
    }

    public ResponseDTO(Boolean success, T result) {
        this.success = success;
        this.result = result;
    }

    public ResponseDTO(Boolean success, String error, T result) {
        this.success = success;
        this.error = error;
        this.result = result;
    }

}
