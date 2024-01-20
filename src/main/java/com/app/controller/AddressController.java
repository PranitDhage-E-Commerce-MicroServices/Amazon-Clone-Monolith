package com.app.controller;

import com.app.customExceptions.UnexpectedErrorException;
import com.app.dto.ResponseDTO;
import com.app.pojo.Address;
import com.app.service.IAddressService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.java.Log;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/address")
public class AddressController {
    @Autowired
    IAddressService addressService;

    public AddressController() {
        log.info("in " + getClass().getName());
    }

    @GetMapping("/list/{user_id}")
    public ResponseDTO getAllAddressList(@PathVariable String user_id) {
        log.info("in get all address");
            return new ResponseDTO(true, addressService.getAllAddresses(Integer.parseInt(user_id)));
    }

    @PostMapping("/add")
    public ResponseDTO addNewAddress(@RequestBody @Valid Address address) {
        log.info("in add new address");
        Address add = addressService.addAddress(address);
        if (add != null) {
            return new ResponseDTO(true, "Address added successfully");
        }
        throw new UnexpectedErrorException("Error while adding new address");
    }

    @PutMapping("/update/{add_id}")
    public ResponseDTO updateAddress(@PathVariable String add_id, @RequestBody Address address) {
        log.info("in update  address");
        Address add = addressService.updateAddress(Integer.parseInt(add_id), address);
        if (add != null) {
            return new ResponseDTO(true, "Address Updated successfully");
        }
        throw new UnexpectedErrorException("Error while updating address");
    }

    @DeleteMapping("/delete/{add_id}")
    public ResponseDTO deleteAddress(@PathVariable String add_id) {
        log.info("in delete  address");
        return new ResponseDTO(true, addressService.deleteAddress(Integer.parseInt(add_id)));
    }
}