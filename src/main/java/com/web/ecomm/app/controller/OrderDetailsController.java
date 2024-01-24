package com.web.ecomm.app.controller;

import com.web.ecomm.app.dto.APIResponseEntity;
import com.web.ecomm.app.exceptions.ResourceNotFoundException;
import com.web.ecomm.app.pojo.OrderDetails;
import com.web.ecomm.app.service.IOrderDetailsService;
import com.web.ecomm.app.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/orderDetails")
public class OrderDetailsController {

    @Autowired
    IOrderDetailsService orderDetailsService;

    public OrderDetailsController() {
        System.out.println("in" + getClass().getName());
    }

    @GetMapping("/list/{myorder_id}")/*---------------------------Admin/User getOrderDetailsList Done-----------------------------------------------*/
    public APIResponseEntity getOrderDetailsList(@PathVariable String myorder_id) {
        System.out.println("in get all my order details");
         List<OrderDetails> orderDetailsList = orderDetailsService.getOrderDetailsList(Integer.parseInt(myorder_id));
        if (orderDetailsList.size() > 0) {
            return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, orderDetailsList);
        }
        throw new ResourceNotFoundException("order Details list not found for the given myorder id", Constants.ERR_RESOURCE_NOT_FOUND);
    }

}