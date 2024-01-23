package com.app.controller;

import com.app.dto.DashboardCountDTO;
import com.app.dto.ResponseDTO;
import com.app.service.ICategoryService;
import com.app.service.ICompanyService;
import com.app.service.IMyOrderService;
import com.app.service.IProductService;
import com.app.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    IUserService userService;

    @Autowired
    IMyOrderService orderService;

    @Autowired
    IProductService productService;

    @Autowired
    ICompanyService companyService;

    @Autowired
    ICategoryService categoryService;

    public AdminController() {
        System.out.println("in AdminController --" + getClass().getName());
    }

    @GetMapping("/userList")/*--------------------------------------------- Admin getAllUserList Done-------------------------------------------------*/
    public ResponseDTO getAllUserList() {
        System.out.println("in admin get all user list");
            return new ResponseDTO(true, userService.getUsersListAll());
    }

    @PutMapping("/userStatus/{user_id}/{status}")/*--------------------------------------------- Admin changeUserActiveStatus Done-------------------------------------------------*/
    public ResponseDTO changeUserActiveStatus(@PathVariable String user_id, @PathVariable String status) {
        System.out.println("in admin change user active status");
        return new ResponseDTO(true, userService.changeUserActiveStatus(Integer.parseInt(user_id), Integer.parseInt(status)));
    }

    @GetMapping("/allUserOrders")/*--------------------------------------------- Admin getAllUserOrders Done-------------------------------------------------*/
    public ResponseDTO getAllUserOrders() {
        System.out.println("in admin get all user orders");
            return new ResponseDTO(true, orderService.getAllUserOrders());
    }

    @PutMapping("/changeDeliveryStatus/{myorder_id}/{status}")/*--------------------------------------------- Admin changeUserOrderDeliveryStatus Done-------------------------------------------------*/
    public ResponseDTO changeUserOrderDeliveryStatus(@PathVariable String myorder_id, @PathVariable String status) {
        System.out.println("in admin change user order delivery status status");
        return new ResponseDTO(true, orderService.changeUserOrderDeliveryStatus(Integer.parseInt(myorder_id), status));
    }

    @GetMapping("/dashboard-count") /*--------------------------------------------- Admin getAllDashboardCount Done-------------------------------------------------*/
    public ResponseDTO getAllDashboardCount() {
        DashboardCountDTO countDTO = new DashboardCountDTO();
        countDTO.setUserCount(userService.getAllUserCount());
        countDTO.setProductCount(productService.countAllProduct());
        countDTO.setMyOrderCount(orderService.countAllUserOrders());
        countDTO.setActiveOrderCount(orderService.countAllActiveUserOrders());
        countDTO.setCompanyCount(companyService.countAllCompany());
        countDTO.setCategoryCount(categoryService.countAllCategory());
        return new ResponseDTO(true, countDTO);
    }

}
