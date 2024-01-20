package com.app.service;

import com.app.dto.SigninDTO;
import com.app.pojo.Credentials;
import com.app.pojo.User;

import java.util.List;

public interface IUserService {

    User userSignup(User u);

    User userSign(SigninDTO user);

    User getProfile(int id);

    User userUpdate(int id, User u);

    List<User> getUsersListAll();

    Credentials addNewAuth(Credentials credentials);

    String changeUserActiveStatus(int user_id, int status);

    Integer getAllUserCount();
}
