package com.web.ecomm.app.service;

import com.web.ecomm.app.dao.CredentialsRepository;
import com.web.ecomm.app.dao.UserRepository;
import com.web.ecomm.app.dto.SigninDTO;
import com.web.ecomm.app.exceptions.AuthenticationException;
import com.web.ecomm.app.exceptions.ResourceNotFoundException;
import com.web.ecomm.app.pojo.Credentials;
import com.web.ecomm.app.pojo.Role;
import com.web.ecomm.app.pojo.User;
import com.web.ecomm.app.utils.Constants;
import com.web.ecomm.app.utils.EncryptPassword;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public User userSignup(User user) {
        user.setPassword(EncryptPassword.getSHA256Hash(user.getPassword()));
        user.setRole(Role.USER);
        user.setStatus(0);
        user.setDate(new Date());
        addNewAuth(new Credentials(user.getEmail(), user.getPassword()));       // Add Email and Password to credentials table
        return userRepo.save(user);
    }

    @Override
    public User userSignIn(SigninDTO signinDTO) throws AuthenticationException {
        User usr = userRepo.findByEmail(signinDTO.getEmail());
        if (usr == null) throw new AuthenticationException("Account does not exist. Please Signup", Constants.ERR_AUTH);
        User user = userRepo.findByEmailAndPassword(signinDTO.getEmail(), EncryptPassword.getSHA256Hash(signinDTO.getPassword()));
        if (user == null) throw new AuthenticationException("Invalid Password. Enter Correct password", Constants.ERR_AUTH);
        return user;
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

}
