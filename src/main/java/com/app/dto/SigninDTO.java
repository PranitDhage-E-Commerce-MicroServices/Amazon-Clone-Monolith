package com.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SigninDTO {
    @JsonProperty("email")
    private String user_email;

    @JsonProperty("password")
    private String user_password;

    public SigninDTO() {
        System.out.println("in signinDTO const");
    }

    public SigninDTO(String user_email, String user_password) {
        super();
        this.user_email = user_email;
        this.user_password = user_password;
    }

}
