package com.web.ecomm.app.controller;

import com.web.ecomm.app.dto.APIResponseEntity;
import com.web.ecomm.app.dto.DashboardCountDTO;
import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.service.ICategoryService;
import com.web.ecomm.app.service.ICompanyService;
import com.web.ecomm.app.service.IMyOrderService;
import com.web.ecomm.app.service.IProductService;
import com.web.ecomm.app.service.IUserService;
import com.web.ecomm.app.utils.Constants;
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
    
    IUserService userService;

    IMyOrderService orderService;

    IProductService productService;

    ICompanyService companyService;

    ICategoryService categoryService;

    @Autowired
    public AdminController(IUserService userService, 
                           IMyOrderService orderService, 
                           IProductService productService, 
                           ICompanyService companyService, 
                           ICategoryService categoryService) {
        System.out.println("in AdminController --" + getClass().getName());
        this.userService = userService;
        this.orderService = orderService;
        this.productService = productService;
        this.companyService = companyService;
        this.categoryService = categoryService;
    }

    @GetMapping("/userList")/*--------------------------------------------- Admin getAllUserList Done-------------------------------------------------*/
    public APIResponseEntity getAllUserList() throws BusinessException {
        System.out.println("in admin get all user list");
            return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, userService.getUsersListAll());
    }

    @PutMapping("/userStatus/{user_id}/{status}")/*--------------------------------------------- Admin changeUserActiveStatus Done-------------------------------------------------*/
    public APIResponseEntity changeUserActiveStatus(@PathVariable String user_id, @PathVariable String status) throws BusinessException {
        System.out.println("in admin change user active status");
        return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, userService.changeUserActiveStatus(Integer.parseInt(user_id), Integer.parseInt(status)));
    }

    @GetMapping("/allUserOrders")/*--------------------------------------------- Admin getAllUserOrders Done-------------------------------------------------*/
    public APIResponseEntity getAllUserOrders() {
        System.out.println("in admin get all user orders");
            return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, orderService.getAllUserOrders());
    }

    @PutMapping("/changeDeliveryStatus/{myorder_id}/{status}")/*--------------------------------------------- Admin changeUserOrderDeliveryStatus Done-------------------------------------------------*/
    public APIResponseEntity changeUserOrderDeliveryStatus(@PathVariable String myorder_id, @PathVariable String status) {
        System.out.println("in admin change user order delivery status status");
        return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, orderService.changeUserOrderDeliveryStatus(Integer.parseInt(myorder_id), status));
    }

    @GetMapping("/dashboard-count") /*--------------------------------------------- Admin getAllDashboardCount Done-------------------------------------------------*/
    public APIResponseEntity getAllDashboardCount() throws BusinessException {
        DashboardCountDTO countDTO = new DashboardCountDTO();
        countDTO.setUserCount(userService.getAllUserCount());
        countDTO.setProductCount(productService.countAllProduct());
        countDTO.setMyOrderCount(orderService.countAllUserOrders());
        countDTO.setActiveOrderCount(orderService.countAllActiveUserOrders());
        countDTO.setCompanyCount(companyService.countAllCompany());
        countDTO.setCategoryCount(categoryService.countAllCategory());
        return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, countDTO);
    }

}
