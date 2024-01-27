package com.web.ecomm.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.ecomm.app.dao.CredentialsRepository;
import com.web.ecomm.app.dao.UserRepository;
import com.web.ecomm.app.dto.AuthenticationResponse;
import com.web.ecomm.app.dto.SignInRequest;
import com.web.ecomm.app.exceptions.AuthenticationException;
import com.web.ecomm.app.exceptions.ResourceNotFoundException;
import com.web.ecomm.app.pojo.Credentials;
import com.web.ecomm.app.pojo.Role;
import com.web.ecomm.app.pojo.User;
import com.web.ecomm.app.security.JwtService;
import com.web.ecomm.app.token.Token;
import com.web.ecomm.app.token.TokenRepository;
import com.web.ecomm.app.token.TokenType;
import com.web.ecomm.app.utils.Constants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    CredentialsRepository credentialsRepo;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TokenRepository tokenRepository;

    @Override
    public User userSignup(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setStatus(0);
        user.setDate(new Date());
        addNewAuth(new Credentials(user.getEmail(), user.getPassword()));       // Add Email and Password to credentials table
        return userRepo.save(user);
    }

    @Override
    public AuthenticationResponse userSignIn(SignInRequest signinDTO) throws AuthenticationException {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signinDTO.getEmail(),
                        signinDTO.getPassword()
                )
        );

        User user = Optional.ofNullable(userRepo.findByEmail(signinDTO.getEmail())
                .orElseThrow(() -> new AuthenticationException("Account does not exist. Please Signup", Constants.ERR_AUTH)))
                .get();

        String jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public Credentials addNewAuth(Credentials credentials) {
        return credentialsRepo.save(credentials);
    }

    @Override
    public String changeUserActiveStatus(int userId, int status) {
        if (userRepo.existsById(userId)) {
            User user = userRepo.findById(userId).get();
            user.setStatus(status);
            userRepo.save(user);
            return "User Active Status Changed Successfully";
        }
        throw new ResourceNotFoundException("User  not found for given user Id : " + userId, Constants.ERR_RESOURCE_NOT_FOUND);
    }

    @Override
    public Integer getAllUserCount() {
        return userRepo.findAll().stream().filter(user -> user.getRole().equals(Role.USER)).collect(Collectors.toList()).size();
    }

    @Override
    public User getProfile(int id) {
        return userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User  not found for given user Id : " + id, Constants.ERR_RESOURCE_NOT_FOUND));
    }

    @Override
    public User userUpdate(int id, User newUser) {
        if (userRepo.existsById(id)) {
            User user = userRepo.findById(id).get();
            if (newUser.getName() != "") user.setName(newUser.getName());
            if (newUser.getEmail() != "") user.setEmail(newUser.getEmail());
            if (newUser.getPhone() != "") user.setPhone(newUser.getPhone());
            return userRepo.save(user);
        }
        throw new ResourceNotFoundException("User  not found for given user Id : " + id, Constants.ERR_RESOURCE_NOT_FOUND);
    }

    @Override
    public List<User> getUsersListAll() {
        return userRepo.findAllByRole(Role.USER);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUserId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
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
    }

}
