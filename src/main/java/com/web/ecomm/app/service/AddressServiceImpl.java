package com.web.ecomm.app.service;

import com.web.ecomm.app.dao.AddressRepository;
import com.web.ecomm.app.dao.UserRepository;
import com.web.ecomm.app.exceptions.ResourceNotFoundException;
import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.pojo.Address;
import com.web.ecomm.app.utils.Constants;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AddressServiceImpl implements IAddressService {

    @Autowired
    AddressRepository addressRepo;

    @Autowired
    UserRepository userRepo;

    @Override
    public List<Address> getAllAddresses(int userId) throws SystemException {
        return addressRepo.findAllByUserUserId(userId);
    }

    @Override
    public Address addAddress(Address address) {
        return addressRepo.save(address);
    }

    @Override
    public Address updateAddress(int add_id, Address newAddress) {
        Address oldAddress = addressRepo.findById(add_id).orElseThrow(() -> new ResourceNotFoundException("Address not found for given address Id : " + add_id, Constants.ERR_RESOURCE_NOT_FOUND));

        BeanUtils.copyProperties(newAddress, oldAddress);

//        if (newAddress.getAddress() != null) oldAddress.setAddress(newAddress.getAddress());
//        if (newAddress.getCity() != null) oldAddress.setCity(newAddress.getCity());
//        if (newAddress.getState() != null) oldAddress.setState(newAddress.getState());
//        if (newAddress.getCountry() != null) oldAddress.setCountry(newAddress.getCountry());
//        if (newAddress.getPin() != null) oldAddress.setPin(newAddress.getPin());
        return addressRepo.save(oldAddress);
    }

    @Override
    public String deleteAddress(int add_id) {
        if (addressRepo.existsById(add_id)) {
            addressRepo.deleteById(add_id);
            return "Address Deleted Successfully";
        }
        throw new ResourceNotFoundException("Address not found for given address Id : " + add_id, Constants.ERR_RESOURCE_NOT_FOUND);
    }
}
