package com.web.ecomm.app.controller;

import com.web.ecomm.app.dto.APIResponseEntity;
import com.web.ecomm.app.dto.SignInRequest;
import com.web.ecomm.app.pojo.Role;
import com.web.ecomm.app.pojo.User;
import com.web.ecomm.app.service.IUserService;
import com.web.ecomm.app.utils.Constants;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
class UserControllerTest extends TestCase {

    @InjectMocks
    UserController userController;

    @Mock
    IUserService userService;

    @BeforeEach
    @Override
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    void userSignIn() {

        SignInRequest signInRequest = SignInRequest.builder()
                .email("test@gmail.com")
                .password("Test@1234")
                .build();

        User user = User.builder()
                .userId(1)
                .email("test@gmail.com")
                .name("TEST")
                .phone("1234567890")
                .status(1)
                .role(Role.USER)
                .build();

        Mockito.when(userService.userSignIn(ArgumentMatchers.any(SignInRequest.class))).thenReturn(user);

        APIResponseEntity<User> responseEntity = userController.userSignin(signInRequest);
        assertEquals(responseEntity.getCode(), Constants.SUCCESS_CODE);
        assertEquals(responseEntity.getStatus(), Constants.STATUS_SUCCESS);
    }

    @Test
    void userSignup() {
    }

    @Test
    void getUserProfile() {
    }

    @Test
    void updateUserProfile() {
    }
}