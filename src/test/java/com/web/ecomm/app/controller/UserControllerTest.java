package com.web.ecomm.app.controller;

import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.models.request.SignInRequest;
import com.web.ecomm.app.models.response.APIResponseEntity;
import com.web.ecomm.app.models.response.AuthenticationResponse;
import com.web.ecomm.app.pojo.User;
import com.web.ecomm.app.security.LogoutService;
import com.web.ecomm.app.service.IUserService;
import com.web.ecomm.app.testutils.TestConstants;
import com.web.ecomm.app.testutils.TestUtils;
import com.web.ecomm.app.utils.Constants;
import junit.framework.TestCase;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.xml.bind.ValidationException;

@RunWith(MockitoJUnitRunner.class)
class UserControllerTest extends TestCase {

    @InjectMocks
    UserController userController;

    @Mock
    IUserService userService;

    @Mock
    LogoutService logoutService;

    @BeforeEach
    @Override
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @AfterEach
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    void userSignIn() throws BusinessException, SystemException {

        SignInRequest signInRequest = TestUtils.getSignInRequest();

        AuthenticationResponse authResponse = TestUtils.getAuthenticationResponse();

        Mockito.when(userService.userSignIn(ArgumentMatchers.any(SignInRequest.class)))
                .thenReturn(authResponse);

        ResponseEntity<APIResponseEntity<AuthenticationResponse>> response = userController.userSignIn(signInRequest);
        APIResponseEntity<AuthenticationResponse> responseBody = response.getBody();

        assertNotNull(responseBody);
        assertEquals(responseBody.getCode(), Constants.SUCCESS_CODE);
        assertEquals(responseBody.getStatus(), Constants.STATUS_SUCCESS);
        assertEquals(responseBody.getData().getAccessToken(), TestConstants.ACCESS_TOKEN);
    }

    @Test
    void userSignup() throws BusinessException, ValidationException, SystemException {

        User user = TestUtils.getUser();
        AuthenticationResponse authResponse = TestUtils.getAuthenticationResponse();

        Mockito.when(userService.userSignup(ArgumentMatchers.any(User.class)))
                .thenReturn(authResponse);

        ResponseEntity<APIResponseEntity<AuthenticationResponse>> response = userController.userSignup(user);
        APIResponseEntity<AuthenticationResponse> responseBody = response.getBody();

        assertNotNull(responseBody);
        assertEquals(responseBody.getCode(), Constants.SUCCESS_CODE);
        assertEquals(responseBody.getStatus(), Constants.STATUS_SUCCESS);
        assertEquals(responseBody.getData().getAccessToken(), TestConstants.ACCESS_TOKEN);
    }

    @Test
    void getUserProfile() throws Exception {

        User user = TestUtils.getUser();
        Integer id = 1;

        Mockito.when(userService.getProfile(ArgumentMatchers.anyInt()))
                .thenReturn(user);

        ResponseEntity<APIResponseEntity<User>> response = userController.getUserProfile(id);
        APIResponseEntity<User> responseBody = response.getBody();

        assertNotNull(responseBody);
        assertEquals(responseBody.getCode(), Constants.SUCCESS_CODE);
        assertEquals(responseBody.getStatus(), Constants.STATUS_SUCCESS);
        assertEquals(
                responseBody.getData().getUserId(),
                id
        );
    }

    @Test
    void updateUserProfile() throws BusinessException, ValidationException, SystemException {

        User user = TestUtils.getUser();
        String id = "1";

        Mockito.when(userService.userUpdate(
                                ArgumentMatchers.anyInt(), ArgumentMatchers.any(User.class)
                        )
                )
                .thenReturn(user);

        ResponseEntity<APIResponseEntity<User>> response = userController.updateUserProfile(id, user);
        APIResponseEntity<User> responseBody = response.getBody();

        assertNotNull(responseBody);
        assertEquals(responseBody.getCode(), Constants.SUCCESS_CODE);
        assertEquals(responseBody.getStatus(), Constants.STATUS_SUCCESS);
        assertEquals(
                String.valueOf(responseBody.getData().getUserId()),
                id
        );
    }
}