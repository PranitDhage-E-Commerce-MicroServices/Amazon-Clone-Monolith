package com.web.ecomm.app.service;

import com.web.ecomm.app.dao.AddressRepository;
import com.web.ecomm.app.dao.UserRepository;
import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.ResourceNotFoundException;
import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.pojo.Address;
import com.web.ecomm.app.utils.Constants;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@Slf4j
public class AddressServiceImpl implements IAddressService {

    AddressRepository addressRepo;

    UserRepository userRepo;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepo, UserRepository userRepo) {
        this.addressRepo = addressRepo;
        this.userRepo = userRepo;
    }

    @Override
    public List<Address> getAllAddresses(int userId) throws BusinessException {

        try {
            return addressRepo.findAllByUserUserId(userId);
        } catch (SystemException e) {
            log.error("Exception While Getting All Address for Given User Id : {}, Message: {}", userId, e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public Address addAddress(Address address) throws BusinessException {

        try {
            address.setDate(new Date());
            return addressRepo.save(address);
        } catch (Exception e) {
            log.error("Exception While Saving Address: {}", e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public Address updateAddress(int add_id, Address newAddress)
            throws ResourceNotFoundException, BusinessException {

        try {

            Address oldAddress = addressRepo.findById(add_id)
                    .orElseThrow(
                            () -> new ResourceNotFoundException(
                                    "Address not found for given address Id : " + add_id,
                                    Constants.ERR_RESOURCE_NOT_FOUND)
                    );

            if (newAddress.getAddress() != null) oldAddress.setAddress(newAddress.getAddress());
            if (newAddress.getCity() != null) oldAddress.setCity(newAddress.getCity());
            if (newAddress.getState() != null) oldAddress.setState(newAddress.getState());
            if (newAddress.getCountry() != null) oldAddress.setCountry(newAddress.getCountry());
            if (newAddress.getPin() != null) oldAddress.setPin(newAddress.getPin());

            return addressRepo.save(oldAddress);

        } catch (ResourceNotFoundException | BeansException e) {
            log.error("Exception While Updating Address: {}", e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public boolean deleteAddress(int addId)
            throws ResourceNotFoundException, BusinessException {

        try {
            if (!addressRepo.existsById(addId)) {
                throw new ResourceNotFoundException(
                        "Address not found for given Address Id : " + addId,
                        Constants.ERR_RESOURCE_NOT_FOUND);
            }

            addressRepo.deleteById(addId);
            return true;
        } catch (ResourceNotFoundException e) {
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }
}
