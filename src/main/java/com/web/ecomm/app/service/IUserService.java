package com.web.ecomm.app.service;

import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.ResourceNotFoundException;
import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.models.request.SignInRequest;
import com.web.ecomm.app.models.response.AuthenticationResponse;
import com.web.ecomm.app.pojo.Credentials;
import com.web.ecomm.app.pojo.User;

import java.util.List;

public interface IUserService {

    /**
     * User Login
     *
     * @param user User Login Request Body
     * @return Authorization Response with JWT Token
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     * @throws com.web.ecomm.app.exceptions.SystemException SystemException
     */
    AuthenticationResponse userSignIn(SignInRequest user) throws  BusinessException, ResourceNotFoundException;

    /**
     * New User Signup
     *
     * @param user User Signup Request Body
     * @return Authorization Response with JWT Token
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     * @throws com.web.ecomm.app.exceptions.SystemException SystemException
     */
    AuthenticationResponse userSignup(User user) throws  BusinessException, ResourceNotFoundException;

    /**
     * Get User Profile for given user
     *
     * @param id User IDENTIFIER
     * @return User Details for given User Id
     * @throws BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.SystemException   SystemException
     */
    User getProfile(int id) throws BusinessException, Exception;

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
    User userUpdate(int id, User user) throws  BusinessException, ResourceNotFoundException;

    Credentials addNewAuth(Credentials credentials) throws BusinessException;

    /**
     * Get all the users for admin
     *
     * @return List of Users for admin
     * @throws BusinessException BusinessException
     */
    List<User> getUsersListAll() throws BusinessException;

    /**
     * Change User Active Status
     *
     * @return Changed user active status
     * @throws BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.SystemException   SystemException
     */
    boolean changeUserActiveStatus(int user_id, int status) throws  BusinessException, SystemException, ResourceNotFoundException;

    /**
     * Get all dashboard count for admin
     *
     * @return DashboardCountResponse Dashboard Count Response
     * @throws BusinessException BusinessException
     * @throws SystemException   SystemException
     */
    Integer getAllUserCount() throws BusinessException, SystemException;
}
