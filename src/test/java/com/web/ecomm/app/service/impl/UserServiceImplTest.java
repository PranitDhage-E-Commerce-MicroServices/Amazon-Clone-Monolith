package com.web.ecomm.app.service.impl;

import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.models.response.AuthenticationResponse;
import com.web.ecomm.app.pojo.Credentials;
import com.web.ecomm.app.pojo.User;
import com.web.ecomm.app.repository.CredentialsRepository;
import com.web.ecomm.app.repository.UserRepository;
import com.web.ecomm.app.security.JwtService;
import com.web.ecomm.app.testutils.TestConstants;
import com.web.ecomm.app.testutils.TestUtils;
import com.web.ecomm.app.token.TokenRepository;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(MockitoJUnitRunner.class)
class UserServiceImplTest extends TestCase {

    private MockMvc mockMvc;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepo;

    @Mock
    private CredentialsRepository credentialsRepo;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenRepository tokenRepository;

    @BeforeEach
    @Override
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userService).build();
    }

    @AfterEach
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    void addNewAuth() throws BusinessException {

        Credentials credentials = TestUtils.getCredentials();

        Mockito.when(
                        credentialsRepo.save(
                                ArgumentMatchers.any(Credentials.class)
                        )
                )
                .thenReturn(credentials);

        Credentials auth = userService.addNewAuth(credentials);

        assertNotNull(auth);
        assertEquals(auth.getUserId(), TestConstants.USER_ID);
        assertEquals(auth.getEmail(), TestConstants.EMAIL);
        assertEquals(auth.getPassword(), TestConstants.PASSWORD);
    }

    //    @Test
    void addNewAuthException() throws BusinessException {

        Credentials credentials = TestUtils.getCredentials();

        Mockito.when(
                        credentialsRepo.save(
                                ArgumentMatchers.any(Credentials.class)
                        )
                )
                .thenReturn(credentials);

        assertThrows(
                BusinessException.class,
                () -> userService.addNewAuth(credentials),
                "Failed to add new auth"
        );

    }


    @Test
    void userSignup() throws BusinessException {

        User user = TestUtils.getUser();

        Mockito.when(passwordEncoder.encode(ArgumentMatchers.anyString()))
                .thenReturn(TestConstants.ENCODE);

        Mockito.when(userRepo.save(ArgumentMatchers.any(User.class)))
                .thenReturn(user);

        Mockito.when(jwtService.generateToken(ArgumentMatchers.any(User.class)))
                .thenReturn(TestConstants.ACCESS_TOKEN);

        Mockito.when(jwtService.generateRefreshToken(ArgumentMatchers.any(User.class)))
                .thenReturn(TestConstants.REFRESH_TOKEN);

        Mockito.when(tokenRepository.findAllValidTokenByUser(TestConstants.USER_ID))
                .thenReturn(new ArrayList<>());

        Mockito.when(tokenRepository.saveAll(ArgumentMatchers.any(List.class)))
                .thenReturn(new ArrayList<>());

        AuthenticationResponse response = userService.userSignup(user);

        assertNotNull(response);
        assertEquals(response.getAccessToken(), TestConstants.ACCESS_TOKEN);
        assertEquals(response.getRefreshToken(), TestConstants.REFRESH_TOKEN);

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

        try {

            User user = TestUtils.getUser();

            Mockito.when(userRepo.findById(ArgumentMatchers.anyInt()))
                    .thenReturn(Optional.ofNullable(user));

            User profile = userService.getProfile(TestConstants.USER_ID);

            assertNotNull(profile);
            assertEquals(user.getUserId(), profile.getUserId());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void getProfileException() {

        Mockito.when(userRepo.findById(ArgumentMatchers.anyInt()))
                .thenReturn(null);

        assertThrows(
                BusinessException.class,
                () -> userService.getProfile(TestConstants.USER_ID),
                "User  not found for given user Id : " + TestConstants.USER_ID
        );

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