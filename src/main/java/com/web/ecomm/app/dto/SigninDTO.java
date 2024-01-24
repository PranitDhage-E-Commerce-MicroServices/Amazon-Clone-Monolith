package com.web.ecomm.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SigninDTO {
    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;
}
