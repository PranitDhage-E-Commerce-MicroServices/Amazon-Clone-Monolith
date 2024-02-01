package com.web.ecomm.app.testutils;

import com.web.ecomm.app.models.request.SignInRequest;
import com.web.ecomm.app.models.response.AuthenticationResponse;
import com.web.ecomm.app.pojo.Credentials;
import com.web.ecomm.app.pojo.Role;
import com.web.ecomm.app.pojo.User;

public class TestUtils {

    public static User getUser() {

        return User.builder()
                .userId(1)
                .email("test@gmail.com")
                .name("TEST")
                .phone("1234567890")
                .status(1)
                .role(Role.USER)
                .build();
    }

    public static SignInRequest getSignInRequest() {

        return SignInRequest.builder()
                .email("test@gmail.com")
                .password("Test@1234")
                .build();
    }

    public static AuthenticationResponse getAuthenticationResponse() {

        return AuthenticationResponse.builder()
                .accessToken(TestConstants.ACCESS_TOKEN)
                .refreshToken(TestConstants.ACCESS_TOKEN)
                .build();
    }

    public static Credentials getCredentials() {

        return Credentials.builder()
                .userId(1)
                .email("test@gmail.com")
                .password("Password@12345")
                .build();
    }
}
