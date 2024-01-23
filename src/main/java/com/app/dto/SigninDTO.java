package com.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SigninDTO {
    @JsonProperty("email")
    private String userEmail;

    @JsonProperty("password")
    private String userPassword;

    public SigninDTO() {
        System.out.println("in signinDTO const");
    }

    public SigninDTO(String userEmail, String userPassword) {
        super();
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

}
