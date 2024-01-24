package com.web.ecomm.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/hello")
public class HelloWorldController {

    @GetMapping(
            value = "/world",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> getAllAddressList() {

        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }

}
