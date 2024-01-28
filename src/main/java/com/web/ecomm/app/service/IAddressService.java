package com.web.ecomm.app.service;

import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.ResourceNotFoundException;
import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.exceptions.ValidationException;
import com.web.ecomm.app.pojo.Address;

import java.util.List;

public interface IAddressService {

    /**
     * Get all the addresses for given user
     *
     * @param userId User IDENTIFIER
     * @return List of Addresses for given User Id
     * @throws BusinessException BusinessException
     * @throws SystemException   SystemException
     */
    List<Address> getAllAddresses(int userId) throws SystemException, BusinessException;

    /**
     * Saves new address for given user
     *
     * @param address Address Request Body
     * @return Saved Address
     * @throws BusinessException BusinessException
     */
    Address addAddress(Address address) throws BusinessException, ValidationException;

    /**
     * Updates address for given address Id
     *
     * @param addId   Address Identifier
     * @param address Address Request Body
     * @return Updated Address
     * @throws BusinessException BusinessException
     */
    Address updateAddress(int addId, Address address) throws BusinessException, ValidationException;

    /**
     * Deletes address for given address Id
     *
     * @param addId Address Identifier
     * @return Deleted Address Status
     * @throws BusinessException BusinessException
     */
    boolean deleteAddress(int addId) throws ResourceNotFoundException, BusinessException;
}
