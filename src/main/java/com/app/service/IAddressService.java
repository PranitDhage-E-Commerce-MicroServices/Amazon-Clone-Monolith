package com.app.service;

import com.app.pojo.Address;

import java.util.List;

public interface IAddressService {
    List<Address> getAllAddresses(int user_id);

    Address addAddress(Address address);

    Address updateAddress(int add_id, Address address);

    String deleteAddress(int add_id);
}
