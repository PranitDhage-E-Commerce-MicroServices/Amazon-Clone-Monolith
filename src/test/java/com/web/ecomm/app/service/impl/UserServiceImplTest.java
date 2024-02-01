package com.web.ecomm.app.service.impl;

import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.pojo.Credentials;
import com.web.ecomm.app.repository.CredentialsRepository;
import com.web.ecomm.app.repository.UserRepository;
import com.web.ecomm.app.testutils.TestUtils;
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

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(MockitoJUnitRunner.class)
class UserServiceImplTest extends TestCase {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepo;

    @Mock
    CredentialsRepository credentialsRepo;

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
    void addNewAuth() throws BusinessException {

        Credentials credentials = TestUtils.getCredentials();
        Integer userId = 1;
        String email = "test@gmail.com";
        String password = "Password@12345";

        Mockito.when(
                        credentialsRepo.save(
                                ArgumentMatchers.any(Credentials.class)
                        )
                )
                .thenReturn(credentials);

        Credentials auth = userService.addNewAuth(credentials);

        assertNotNull(auth);
        assertEquals(auth.getUserId(), userId);
        assertEquals(auth.getEmail(), email);
        assertEquals(auth.getPassword(), password);
    }

    @Test
    void addNewAuthException() {

        Credentials credentials = TestUtils.getCredentials();

        Mockito.when(credentialsRepo.save(credentials))
                .thenThrow(Exception.class);

        assertThrows(
                BusinessException.class,
                () -> userService.addNewAuth(credentials)
        );
    }

    @Test
    void userSignup() {
    }

    @Test
    void userSignIn() {
    }

    @Test
    void changeUserActiveStatus() {
    }

    @Test
    void getAllUserCount() {
    }

    @Test
    void getProfile() {
    }

    @Test
    void userUpdate() {
    }

    @Test
    void getUsersListAll() {
    }

    @Test
    void refreshToken() {
    }
}