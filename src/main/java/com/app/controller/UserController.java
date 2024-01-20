package com.app.controller;

import com.app.customExceptions.AuthenticationException;
import com.app.customExceptions.ResourceNotFoundException;
import com.app.customExceptions.UnexpectedErrorException;
import com.app.dto.ResponseDTO;
import com.app.dto.SigninDTO;
import com.app.pojo.User;
import com.app.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        throw new AuthenticationException("Invalid Email or Password");
    }

    @PostMapping("/signup") /*--------------------------------------------- User Signup Done-------------------------------------------------*/
    public ResponseDTO userSignup(@RequestBody User user, BindingResult bindingResult) {
        System.out.println("in user signup : " + user);
        if (bindingResult.hasErrors()) {
            throw new AuthenticationException("Invalid Data");
        } else {
            User usr = userService.userSignup(user);
            if (usr != null) {
                return new ResponseDTO(true, "Signed Up Successfully");
            }
            throw new UnexpectedErrorException("Error While Sign up");
        }
    }

    @GetMapping("/profile/{id}")/*--------------------------------------------- User getUserProfile Done-------------------------------------------------*/
    public ResponseDTO getUserProfile(@PathVariable int id) {
        System.out.println("in user get profile : " + id);
        User foundUser = userService.getProfile(id);
        if (foundUser != null) {
            return new ResponseDTO(true, foundUser);
        }
        throw new ResourceNotFoundException("User not found for given user id");
    }

    @PutMapping("/UpdateProfile/{id}")/*--------------------------------------------- User updateUserProfile Done-------------------------------------------------*/
    public ResponseDTO updateUserProfile(@PathVariable String id, @RequestBody User user) {
        System.out.println("in user update profile : " + id);
        User updatedUser = userService.userUpdate(Integer.parseInt(id), user);
        if (updatedUser != null) {
            return new ResponseDTO(true, "user profile updated successfully");
        }
        throw new UnexpectedErrorException("Error while updating user profile");
    }

}
