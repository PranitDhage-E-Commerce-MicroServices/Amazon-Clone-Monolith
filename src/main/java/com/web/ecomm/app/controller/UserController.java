package com.web.ecomm.app.controller;

import com.web.ecomm.app.exceptions.AuthenticationException;
import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.models.request.SignInRequest;
import com.web.ecomm.app.models.response.APIResponseEntity;
import com.web.ecomm.app.models.response.AuthenticationResponse;
import com.web.ecomm.app.pojo.User;
import com.web.ecomm.app.security.LogoutService;
import com.web.ecomm.app.service.IUserService;
import com.web.ecomm.app.utils.Constants;
import com.web.ecomm.app.utils.Error;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.ValidationException;

@RestController
@CrossOrigin
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final IUserService userService;

    private final LogoutService logoutService;

    @Autowired
    public UserController(final IUserService userService, final LogoutService logoutService) {
        this.userService = userService;
        this.logoutService = logoutService;
    }

    /**
     * User Login
     *
     * @param user User Login Request Body
     * @return Authorization Response with JWT Token
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     * @throws com.web.ecomm.app.exceptions.SystemException SystemException
     */
    @Operation(summary = "User Login",
            description = "This API is used to Login User",
            tags = {"Checks"},
            method = "POST"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Unexpected Error", content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "403", description = "Access Denied - User is either invalid or is not entitled to requested api action", content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "404", description = "Entity Not Found", content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Error.class)))
            }
    )
    @PostMapping(
            value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<AuthenticationResponse>> userSignIn(
            @Parameter(description = "Login Request", required = true) @RequestBody @Valid SignInRequest user
    ) throws AuthenticationException, BusinessException, SystemException {

        log.info("Logging in User Request: {}", user);

        AuthenticationResponse auth = userService.userSignIn(user);

        APIResponseEntity<AuthenticationResponse> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                auth
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * New User Signup
     *
     * @param user User Signup Request Body
     * @return Authorization Response with JWT Token
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     * @throws com.web.ecomm.app.exceptions.SystemException SystemException
     */
    @Operation(summary = "User Signup",
            description = "This API is used to Signup New User",
            tags = {"Checks"},
            method = "POST"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Unexpected Error", content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "403", description = "Access Denied - User is either invalid or is not entitled to requested api action", content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "404", description = "Entity Not Found", content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Error.class)))
            }
    )
    @PostMapping(
            value = "/signup",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<AuthenticationResponse>> userSignup(
            @Parameter(description = "Signup Request", required = true) @RequestBody @Valid User user
    ) throws ValidationException, BusinessException, SystemException {

        log.info("Signing up New User Request: {}", user);

        AuthenticationResponse authResponse = userService.userSignup(user);

        APIResponseEntity<AuthenticationResponse> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                authResponse
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get User Profile for given user
     *
     * @param id User IDENTIFIER
     * @return User Details for given User Id
     * @throws BusinessException BusinessException
     * @throws SystemException   SystemException
     */
    @Operation(summary = "Get User Profile for given user",
            description = "This API is used to Get User Profile for given user Id",
            tags = {"Checks"},
            method = "GET"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Unexpected Error", content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "403", description = "Access Denied - User is either invalid or is not entitled to requested api action", content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "404", description = "Entity Not Found", content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Error.class)))
            }
    )
    @GetMapping(
            value = "/profile/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<User>> getUserProfile(
            @Parameter(description = "User Identifier", required = true) @PathVariable("id") int id
    ) throws BusinessException, Exception {

        log.info("Getting User Profile for given User Id : " + id);

        User foundUser = userService.getProfile(id);

        APIResponseEntity<User> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                foundUser
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Updates User Details for given address Id
     *
     * @param id User IDENTIFIER
     * @param user User Request Body
     * @return Updated User
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     * @throws com.web.ecomm.app.exceptions.SystemException SystemException
     */
    @Operation(summary = "Updates User Details for given address Id",
            description = "This API is used to Updates User Details for given address Id",
            tags = {"Checks"},
            method = "PUT"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Unexpected Error", content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "403", description = "Access Denied - User is either invalid or is not entitled to requested api action", content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "404", description = "Entity Not Found", content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Error.class)))
            }
    )
    @PutMapping(
            value = "/Update-profile/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<User>> updateUserProfile(
            @Parameter(description = "User Identifier", required = true) @PathVariable int id,
            @Parameter(description = "User Request", required = true) @RequestBody @Valid User user
    ) throws BusinessException, SystemException, ValidationException {

        log.info("Updating User Profile for given User Id: {}, Request: {} ", id, user);

        User updatedUser = userService.userUpdate(id, user);

        APIResponseEntity<User> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                updatedUser
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /**
     * Logout User and invalidate JWT Token
     *
     * @return Success Response
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     * @throws com.web.ecomm.app.exceptions.SystemException SystemException
     */
    @Operation(summary = "Logout User and invalidate JWT Token",
            description = "This API is used to Logout User and invalidate JWT Token",
            tags = {"Checks"},
            method = "PUT"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Unexpected Error", content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "403", description = "Access Denied - User is either invalid or is not entitled to requested api action", content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "404", description = "Entity Not Found", content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Error.class)))
            }
    )
    @PutMapping(
            value = "/logout",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<String>> logoutUser(
            HttpServletRequest request
    ) throws BusinessException, SystemException, ValidationException {

        logoutService.logout(request, null, null);

        APIResponseEntity<String> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                "Logout Successful"
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
