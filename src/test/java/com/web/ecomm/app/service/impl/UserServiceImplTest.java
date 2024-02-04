package com.web.ecomm.app.service.impl;

import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.ResourceNotFoundException;
import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.models.response.AuthenticationResponse;
import com.web.ecomm.app.pojo.Credentials;
import com.web.ecomm.app.pojo.Role;
import com.web.ecomm.app.pojo.User;
import com.web.ecomm.app.repository.CredentialsRepository;
import com.web.ecomm.app.repository.UserRepository;
import com.web.ecomm.app.security.JwtService;
import com.web.ecomm.app.testutils.TestConstants;
import com.web.ecomm.app.testutils.TestUtils;
import com.web.ecomm.app.token.Token;
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
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userService).build();
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

        Mockito.when(tokenRepository.saveAll(ArgumentMatchers.anyList()))
                .thenReturn(new ArrayList<>());

        Mockito.when(tokenRepository.save(ArgumentMatchers.any(Token.class)))
                .thenReturn(TestUtils.getToken());

        AuthenticationResponse response = userService.userSignup(user);

        assertNotNull(response);
        assertEquals(response.getAccessToken(), TestConstants.ACCESS_TOKEN);
        assertEquals(response.getRefreshToken(), TestConstants.REFRESH_TOKEN);

    }

    @Test
    void userSignIn() throws BusinessException {

        Mockito.when(userRepo.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(Optional.ofNullable(TestUtils.getUser()));

        Mockito.when(jwtService.generateToken(ArgumentMatchers.any(User.class)))
                .thenReturn(TestConstants.ACCESS_TOKEN);

        Mockito.when(jwtService.generateRefreshToken(ArgumentMatchers.any(User.class)))
                .thenReturn(TestConstants.REFRESH_TOKEN);

        Mockito.when(tokenRepository.findAllValidTokenByUser(TestConstants.USER_ID))
                .thenReturn(new ArrayList<>());

        Mockito.when(tokenRepository.saveAll(ArgumentMatchers.anyList()))
                .thenReturn(new ArrayList<>());

        Mockito.when(tokenRepository.save(ArgumentMatchers.any(Token.class)))
                .thenReturn(TestUtils.getToken());

        AuthenticationResponse response = userService.userSignIn(TestUtils.getSignInRequest());

        assertNotNull(response);
        assertEquals(response.getAccessToken(), TestConstants.ACCESS_TOKEN);
        assertEquals(response.getRefreshToken(), TestConstants.REFRESH_TOKEN);

    }

    @Test
    void changeUserActiveStatus() throws BusinessException, SystemException {

        User user = TestUtils.getUser();

        Mockito.when(userRepo.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.ofNullable(user));

        Mockito.when(userRepo.save(ArgumentMatchers.any(User.class)))
                .thenReturn(user);

        boolean activeStatus = userService.changeUserActiveStatus(
                TestConstants.USER_ID, TestConstants.STATUS_ACTIVE
        );

        assertTrue(activeStatus);

    }

    @Test
    void changeUserActiveStatusResourceNotFoundException() {

        Mockito.when(userRepo.findById(ArgumentMatchers.anyInt()))
                .thenThrow(ResourceNotFoundException.class);

        assertThrows(
                BusinessException.class,
                () -> userService.changeUserActiveStatus(
                        TestConstants.USER_ID, TestConstants.STATUS_ACTIVE
                ),
                "User  not found for given user Id : " + TestConstants.USER_ID
        );
    }


    @Test
    void getAllUserCount() throws BusinessException, SystemException {

        List<User> userList = List.of(TestUtils.getUser());

        Mockito.when(userRepo.findAll()).thenReturn(userList);

        int count = userService.getAllUserCount();
        assertEquals(userList.size(), count);
    }

    @Test
    void getProfile() {

        try {

            User user = TestUtils.getUser();

            Mockito.when(userRepo.findById(ArgumentMatchers.anyInt()))
                    .thenReturn(Optional.ofNullable(user));

            User profile = userService.getProfile(TestConstants.USER_ID);

            assertNotNull(profile);
            assert user != null;
            assertEquals(user.getUserId(), profile.getUserId());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void getProfileBusinessException() {

        Mockito.when(userRepo.findById(ArgumentMatchers.anyInt()))
                .thenReturn(null);

        assertThrows(
                BusinessException.class,
                () -> userService.getProfile(TestConstants.USER_ID),
                "User  not found for given user Id : " + TestConstants.USER_ID
        );
    }

    @Test
    void getProfileResourceNotFoundException() {

        Mockito.when(userRepo.findById(ArgumentMatchers.anyInt()))
                .thenThrow(ResourceNotFoundException.class);

        assertThrows(
                BusinessException.class,
                () -> userService.getProfile(TestConstants.USER_ID),
                "User  not found for given user Id : " + TestConstants.USER_ID
        );
    }

    @Test
    void userUpdate() throws BusinessException {

        User user = TestUtils.getUser();

        Mockito.when(userRepo.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.ofNullable(user));

        Mockito.when(userRepo.save(ArgumentMatchers.any(User.class)))
                .thenReturn(user);

        User updatedUser = userService.userUpdate(TestConstants.USER_ID, user);

        assertNotNull(updatedUser);
        assert user != null;
        assertEquals(user.getEmail(), updatedUser.getEmail());
    }

    @Test
    void getUsersListAll() throws BusinessException {

        List<User> userList = List.of(TestUtils.getUser());

        Mockito.when(userRepo.findAllByRole(ArgumentMatchers.any(Role.class)))
                .thenReturn(userList);

        List<User> listAll = userService.getUsersListAll();

        assertNotNull(listAll);
        assertEquals(userList.size(), listAll.size());
    }

    @Test
    void saveUserToken() throws BusinessException {

        Token token = TestUtils.getToken();
        User user = TestUtils.getUser();

        Mockito.when(tokenRepository.save(ArgumentMatchers.any(Token.class)))
                .thenReturn(token);

        Token savedToken = userService.saveUserToken(
                user, TestConstants.ACCESS_TOKEN
        );

        assertNotNull(savedToken);
    }

    @Test
    void revokeAllUserTokens() throws BusinessException {

        Token token = TestUtils.getToken();
        List<Token> tokenList = List.of(token);
        User user = TestUtils.getUser();

        Mockito.when(tokenRepository.findAllValidTokenByUser(ArgumentMatchers.anyInt()))
                .thenReturn(tokenList);

        Mockito.when(tokenRepository.saveAll(ArgumentMatchers.anyList()))
                .thenReturn(tokenList);

        List<Token> revokedTokens = userService.revokeAllUserTokens(user);

        assertNotNull(revokedTokens);
        assertEquals(tokenList.size(), revokedTokens.size());

    }

}