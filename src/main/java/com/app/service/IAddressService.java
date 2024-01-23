package com.app.service;

import com.app.exceptions.SystemException;
import com.app.pojo.Address;

import java.util.List;

public interface IAddressService {
    List<Address> getAllAddresses(int user_id) throws SystemException;

    Address addAddress(Address address);

    Address updateAddress(int add_id, Address address);

    String deleteAddress(int add_id);
}
