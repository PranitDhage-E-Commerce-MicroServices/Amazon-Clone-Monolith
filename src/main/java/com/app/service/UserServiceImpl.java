package com.app.service;

import com.app.customExceptions.AuthenticationException;
import com.app.customExceptions.ResourceNotFoundException;
import com.app.dao.CredentialsRepository;
import com.app.dao.UserRepository;
import com.app.dto.SigninDTO;
import com.app.pojo.Credentials;
import com.app.pojo.Role;
import com.app.pojo.User;
import com.app.utils.EncryptPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    CredentialsRepository credentialsRepo;

    public UserServiceImpl() {
    }

    @Override
    public User userSign(SigninDTO signinDTO) {
        User usr = userRepo.findByUserEmail(signinDTO.getUser_email());
        if (usr == null) throw new AuthenticationException("Account does not exist. Please Signup");
        User user = userRepo.findByUserEmailAndUserPassword(signinDTO.getUser_email(), EncryptPassword.getSHA256Hash(signinDTO.getUser_password()));
        if (user == null) throw new AuthenticationException("Invalid Password. Enter Correct password");
        return user;
    }

    @Override
    public Credentials addNewAuth(Credentials credentials) {
        return credentialsRepo.save(credentials);
    }

    @Override
    public String changeUserActiveStatus(int user_id, int status) {
        if (userRepo.existsById(user_id)) {
            User user = userRepo.findById(user_id).get();
            user.setUserStatus(status);
            userRepo.save(user);
            return "User Active Status Changed Successfully";
        }
        throw new ResourceNotFoundException("User  not found for given user Id : " + user_id);
    }

    @Override
    public Integer getAllUserCount() {
        return userRepo.findAll().stream().filter(user -> user.getUserRole().equals(Role.USER)).collect(Collectors.toList()).size();
    }


    @Override
    public User userSignup(User user) {
        user.setUserPassword(EncryptPassword.getSHA256Hash(user.getUserPassword()));
        user.setUserRole(Role.USER);
        user.setUserStatus(0);
        addNewAuth(new Credentials(user.getUserEmail(), user.getUserPassword()));       // Add userEmail and Password to credentials table
        return userRepo.save(user);
    }

    @Override
    public User getProfile(int id) {
        return userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User  not found for given user Id : " + id));
    }

    @Override
    public User userUpdate(int id, User newUser) {
        if (userRepo.existsById(id)) {
            User user = userRepo.findById(id).get();
            if (newUser.getUserName() != "") user.setUserName(newUser.getUserName());
            if (newUser.getUserEmail() != "") user.setUserEmail(newUser.getUserEmail());
            if (newUser.getUserPhone() != "") user.setUserPhone(newUser.getUserPhone());
            return userRepo.save(user);
        }
        throw new ResourceNotFoundException("User  not found for given user Id : " + id);
    }

    @Override
    public List<User> getUsersListAll() {
        return userRepo.findAllByUserRole(Role.USER);
    }

}
