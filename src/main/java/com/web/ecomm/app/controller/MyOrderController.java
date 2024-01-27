package com.web.ecomm.app.controller;

import com.web.ecomm.app.models.response.APIResponseEntity;
import com.web.ecomm.app.pojo.Myorder;
import com.web.ecomm.app.service.IMyOrderService;
import com.web.ecomm.app.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/myOrder")
public class MyOrderController {
    @Autowired
    IMyOrderService myOrderService;

    public MyOrderController() {
        System.out.println("in MyOrderController -- " + getClass().getName());
    }

    @GetMapping("/list")
    public APIResponseEntity getAllUserOrderList() {
        System.out.println("in get all user Order list");/*-------------------------------Admin getAllUserOrderList Done-----------------------------------*/
            return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, myOrderService.getAllUserOrders());
    }


    @GetMapping("/list/{user_id}")/*-------------------------------Admin getMyOrderList Done-----------------------------------*/
    public APIResponseEntity getMyOrderList(@PathVariable String user_id) {
        System.out.println("in get all myOrder list");
            return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, myOrderService.getMyOrderList(Integer.parseInt(user_id)));
    }

    @PostMapping("/checkout")/*-------------------------------User checkoutMyOrder Done-----------------------------------*/
    public APIResponseEntity checkoutMyOrder(@RequestBody Myorder myorder) {
        System.out.println("in add new myOrder" + myorder);
        return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, myOrderService.checkoutMyOrder(myorder));
    }

    @PutMapping("/update/{myOrder_id}/{status}")/*-------------------------------Admin/User updateMyOrderStatus Done-----------------------------------*/
    public APIResponseEntity updateMyOrderStatus(@PathVariable String myOrder_id, @PathVariable String status) {
        System.out.println("in update  update myOrder status");
        return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, myOrderService.updateMyOrderStatus(Integer.parseInt(myOrder_id), status));
    }

    @DeleteMapping("/delete/{myOrder_id}")/*-------------------------------Admin/User deleteMyOrder Done-----------------------------------*/
    public APIResponseEntity deleteMyOrder(@PathVariable String myOrder_id) {
        System.out.println("in delete  myOrder");
        return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, myOrderService.deleteMyOrder(Integer.parseInt(myOrder_id)));
    }

}
