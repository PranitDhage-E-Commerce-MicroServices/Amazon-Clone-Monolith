package com.web.ecomm.app.controller;

import com.web.ecomm.app.dto.APIResponseEntity;
import com.web.ecomm.app.dto.AuthenticationResponse;
import com.web.ecomm.app.dto.SignInRequest;
import com.web.ecomm.app.exceptions.AuthenticationException;
import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.ResourceNotFoundException;
import com.web.ecomm.app.exceptions.UnexpectedErrorException;
import com.web.ecomm.app.pojo.User;
import com.web.ecomm.app.security.LogoutService;
import com.web.ecomm.app.service.IUserService;
import com.web.ecomm.app.utils.APIUtil;
import com.web.ecomm.app.utils.Constants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
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

    @Autowired
    IUserService userService;

    @Autowired
    LogoutService logoutService;

    public UserController() {
        System.out.println("in " + getClass().getName());
    }

    @PostMapping("/login")   /*--------------------------------------------- Admin/User Login Done-------------------------------------------------*/
    public APIResponseEntity<AuthenticationResponse> userSignin(@RequestBody SignInRequest user)
            throws AuthenticationException, BusinessException {
        AuthenticationResponse foundUser = userService.userSignIn(user);
        return new APIResponseEntity<>(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, foundUser);
    }

    @PostMapping("/signup")
    public APIResponseEntity userSignup(@RequestBody User user, BindingResult bindingResult)
            throws ValidationException, BusinessException {
        log.info("User Request Body : " + user.toString());

        APIUtil.validateRequestBody(bindingResult);
        AuthenticationResponse authResponse = userService.userSignup(user);

        return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, authResponse);
    }

    @GetMapping("/profile/{id}")/*--------------------------------------------- User getUserProfile Done-------------------------------------------------*/
    public APIResponseEntity getUserProfile(@PathVariable int id)
            throws BusinessException {
        System.out.println("in user get profile : " + id);
        User foundUser = userService.getProfile(id);
        if (foundUser != null) {
            return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, foundUser);
        }
        throw new ResourceNotFoundException("User not found for given user id", Constants.ERR_RESOURCE_NOT_FOUND);
    }

    @PutMapping("/UpdateProfile/{id}")/*--------------------------------------------- User updateUserProfile Done-------------------------------------------------*/
    public APIResponseEntity updateUserProfile(@PathVariable String id, @RequestBody User user)
            throws BusinessException {
        System.out.println("in user update profile : " + id);
        User updatedUser = userService.userUpdate(Integer.parseInt(id), user);
        if (updatedUser != null) {
            return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, "user profile updated successfully");
        }
        throw new UnexpectedErrorException("Error while updating user profile", Constants.ERR_DEFAULT);
    }

    @GetMapping("/logout")/*--------------------------------------------- User updateUserProfile Done-------------------------------------------------*/
    public APIResponseEntity<String> logoutUser(HttpServletRequest request) {
        logoutService.logout(request, null, null);
        return new APIResponseEntity<String>(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, "Logout Successful");
    }

}
