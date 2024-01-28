package com.web.ecomm.app.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.ecomm.app.exceptions.AuthenticationException;
import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.ResourceNotFoundException;
import com.web.ecomm.app.models.request.SignInRequest;
import com.web.ecomm.app.models.response.AuthenticationResponse;
import com.web.ecomm.app.pojo.Credentials;
import com.web.ecomm.app.pojo.Role;
import com.web.ecomm.app.pojo.User;
import com.web.ecomm.app.repository.CredentialsRepository;
import com.web.ecomm.app.repository.UserRepository;
import com.web.ecomm.app.security.JwtService;
import com.web.ecomm.app.service.IUserService;
import com.web.ecomm.app.token.Token;
import com.web.ecomm.app.token.TokenRepository;
import com.web.ecomm.app.token.TokenType;
import com.web.ecomm.app.utils.Constants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private UserRepository userRepo;

    private CredentialsRepository credentialsRepo;

    private JwtService jwtService;

    private AuthenticationManager authenticationManager;

    private PasswordEncoder passwordEncoder;

    private TokenRepository tokenRepository;

    @Autowired
    public UserServiceImpl(final UserRepository userRepo,
                           final CredentialsRepository credentialsRepo,
                           final JwtService jwtService,
                           final AuthenticationManager authenticationManager,
                           final PasswordEncoder passwordEncoder,
                           final TokenRepository tokenRepository) {

        this.userRepo = userRepo;
        this.credentialsRepo = credentialsRepo;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Credentials addNewAuth(Credentials credentials) throws BusinessException {
        try {
            return credentialsRepo.save(credentials);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public AuthenticationResponse userSignup(User user) throws BusinessException {

        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(Role.USER);
            user.setStatus(0);
            user.setDate(new Date());

            // Add Email and Password to credentials table
            addNewAuth(new Credentials(user.getEmail(), user.getPassword()));

            User savedUser = userRepo.save(user);

            String jwtToken = jwtService.generateToken(savedUser);
            var refreshToken = jwtService.generateRefreshToken(savedUser);
            revokeAllUserTokens(savedUser);
            saveUserToken(savedUser, jwtToken);

            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public AuthenticationResponse userSignIn(SignInRequest signInRequest)
            throws AuthenticationException, BusinessException {

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signInRequest.getEmail(),
                            signInRequest.getPassword()
                    )
            );

            User user = Optional.ofNullable(userRepo.findByEmail(signInRequest.getEmail())
                            .orElseThrow(
                                    () -> new AuthenticationException(
                                            "Account does not exist. Please Signup",
                                            Constants.ERR_AUTH)))
                    .get();

            String jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (AuthenticationException e) {
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public String changeUserActiveStatus(int userId, int status)
            throws BusinessException, ResourceNotFoundException {

        try {

            User user = userRepo.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException(
                            "User  not found for given user Id : " + userId,
                            Constants.ERR_RESOURCE_NOT_FOUND)
            );

            user.setStatus(status);
            userRepo.save(user);
            return "User Active Status Changed Successfully";

        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public Integer getAllUserCount() throws BusinessException {
        try {
            return userRepo.findAll()
                    .stream()
                    .filter(user -> user.getRole().equals(Role.USER))
                    .toList().size();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public User getProfile(int id) throws BusinessException, Exception {
        try {
            return userRepo.findById(id)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "User  not found for given user Id : " + id,
                                    Constants.ERR_RESOURCE_NOT_FOUND)
                    );
        } catch (ResourceNotFoundException e) {
            log.info("User not found for given user Id : {}", id);
            throw new BusinessException(e.getMessage(), e.getCode());
        } catch (Exception e) {
            log.info("Exception occurred while getting User Profile for User Id: {}", id);
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public User userUpdate(int id, User newUser)
            throws ResourceNotFoundException, BusinessException {

        try {

            User user = userRepo.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException(
                            "User  not found for given user Id : " + id,
                            Constants.ERR_RESOURCE_NOT_FOUND)
            );

            if (!StringUtils.equals(newUser.getName(), "")) user.setName(newUser.getName());
            if (!StringUtils.equals(newUser.getEmail(), "")) user.setEmail(newUser.getEmail());
            if (!StringUtils.equals(newUser.getPhone(), "")) user.setPhone(newUser.getPhone());
            return userRepo.save(user);

        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public List<User> getUsersListAll() throws BusinessException {

        try {
            return userRepo.findAllByRole(Role.USER);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    private void saveUserToken(User user, String jwtToken) throws BusinessException {

        try {
            var token = Token.builder()
                    .user(user)
                    .token(jwtToken)
                    .tokenType(TokenType.BEARER)
                    .expired(false)
                    .revoked(false)
                    .build();
            tokenRepository.save(token);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    private void revokeAllUserTokens(User user) throws BusinessException {

        try {
            var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUserId());

            if (validUserTokens.isEmpty())
                return;

            validUserTokens.forEach(token -> {
                token.setExpired(true);
                token.setRevoked(true);
            });

            tokenRepository.saveAll(validUserTokens);
//            tokenRepository.deleteAll(validUserTokens);

        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response)
            throws IOException, BusinessException {

        try {
            final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            final String refreshToken;
            final String userEmail;
            if (authHeader == null || !authHeader.startsWith(Constants.TOKEN_PREFIX)) {
                return;
            }

            refreshToken = authHeader.substring(7);
            userEmail = jwtService.extractUsername(refreshToken);
            if (userEmail != null) {
                var user = this.userRepo.findByEmail(userEmail)
                        .orElseThrow();
                if (jwtService.isTokenValid(refreshToken, user)) {
                    var accessToken = jwtService.generateToken(user);
                    revokeAllUserTokens(user);
                    saveUserToken(user, accessToken);
                    var authResponse = AuthenticationResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build();
                    new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
                }
            }
        } catch (IOException e) {
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

}
