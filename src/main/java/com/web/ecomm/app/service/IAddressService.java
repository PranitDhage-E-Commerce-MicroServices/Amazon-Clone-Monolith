package com.web.ecomm.app.service;

import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.pojo.Address;

import java.util.List;

public interface IAddressService {
    List<Address> getAllAddresses(int user_id) throws SystemException;

    Address addAddress(Address address);

    Address updateAddress(int add_id, Address address);

    String deleteAddress(int add_id);
}
