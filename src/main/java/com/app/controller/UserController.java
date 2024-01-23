package com.app.controller;

import com.app.dto.ResponseDTO;
import com.app.dto.SigninDTO;
import com.app.exceptions.AuthenticationException;
import com.app.exceptions.ResourceNotFoundException;
import com.app.exceptions.UnexpectedErrorException;
import com.app.pojo.User;
import com.app.service.IUserService;
import com.app.utils.Constants;
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

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    public UserController() {
        System.out.println("in " + getClass().getName());
    }

    @PostMapping("/login")   /*--------------------------------------------- Admin/User Login Done-------------------------------------------------*/
    public ResponseDTO userSignin(@RequestBody SigninDTO user) {
        System.out.println("inside Sign in" + user);
        User foundUser = userService.userSign(user);
        if (foundUser != null) {
            return new ResponseDTO(true, foundUser);
        }
        throw new AuthenticationException("Invalid Email or Password", Constants.ERR_AUTH);
    }

    @PostMapping("/signup") /*--------------------------------------------- User Signup Done-------------------------------------------------*/
    public ResponseDTO userSignup(@RequestBody User user, BindingResult bindingResult) {
        System.out.println("in user signup : " + user);
        if (bindingResult.hasErrors()) {
            throw new AuthenticationException("Invalid Data", Constants.ERR_INVALID_DATA);
        } else {
            User usr = userService.userSignup(user);
            if (usr != null) {
                return new ResponseDTO(true, "Signed Up Successfully");
            }
            throw new UnexpectedErrorException("Error While Sign up", Constants.ERR_DEFAULT);
        }
    }

    @GetMapping("/profile/{id}")/*--------------------------------------------- User getUserProfile Done-------------------------------------------------*/
    public ResponseDTO getUserProfile(@PathVariable int id) {
        System.out.println("in user get profile : " + id);
        User foundUser = userService.getProfile(id);
        if (foundUser != null) {
            return new ResponseDTO(true, foundUser);
        }
        throw new ResourceNotFoundException("User not found for given user id", Constants.ERR_RESOURCE_NOT_FOUND);
    }

    @PutMapping("/UpdateProfile/{id}")/*--------------------------------------------- User updateUserProfile Done-------------------------------------------------*/
    public ResponseDTO updateUserProfile(@PathVariable String id, @RequestBody User user) {
        System.out.println("in user update profile : " + id);
        User updatedUser = userService.userUpdate(Integer.parseInt(id), user);
        if (updatedUser != null) {
            return new ResponseDTO(true, "user profile updated successfully");
        }
        throw new UnexpectedErrorException("Error while updating user profile", Constants.ERR_DEFAULT);
    }

}
