package com.web.ecomm.app.service;

import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.exceptions.ValidationException;
import com.web.ecomm.app.pojo.Myorder;

import java.util.List;

public interface IMyOrderService {

    /**
     * Get all the User Order List for given user
     *
     * @param userId User IDENTIFIER
     * @return List of User Order for given User Id
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.SystemException   SystemException
     */
    List<Myorder> getMyOrderList(int userId) throws BusinessException, SystemException, ValidationException;

    /**
     * Get all the MyOrder for Admin
     *
     * @return List of MyOrder for Admin
     * @throws BusinessException BusinessException
     * @throws SystemException   SystemException
     */
    List<Myorder> getAllUserOrders() throws BusinessException, SystemException, ValidationException;

    /**
     * Checkout My Order for given user
     *
     * @param myorder MyOrder Request Body
     * @return Saved Address
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     * @throws com.web.ecomm.app.exceptions.SystemException SystemException
     */
    boolean checkoutMyOrder(Myorder myorder) throws BusinessException, SystemException, ValidationException;

    /**
     * Updates MyOrder Status for given MyOrder Id
     *
     * @param myOrderId MyOrder Identifier
     * @param status MyOrder Status
     * @return Updated MyOrder
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     * @throws com.web.ecomm.app.exceptions.SystemException SystemException
     */
    Myorder updateMyOrderStatus(int myOrderId, String status) throws BusinessException, SystemException, ValidationException;

    /**
     * Deletes MyOrder for given MyOrder Id
     *
     * @param myOrderId MyOrder Identifier
     * @return Deleted MyOrder Status
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.SystemException SystemException
     */
    boolean deleteMyOrder(int myOrderId) throws BusinessException, SystemException, ValidationException;

    /**
     * Change user order delivery status for given MyOrder identifier By Admin
     *
     * @param myOrderId   Order Identifier
     * @param status Order Status
     * @return Updated Status
     * @throws BusinessException BusinessException
     * @throws SystemException SystemException
     */
    boolean changeUserOrderDeliveryStatus(int myOrderId, String status) throws BusinessException, SystemException, ValidationException;

    /**
     * Get all dashboard count for admin
     *
     * @return DashboardCountResponse Dashboard Count Response
     * @throws BusinessException BusinessException
     * @throws SystemException   SystemException
     */
    Integer countAllUserOrders() throws BusinessException, SystemException, ValidationException;

    /**
     * Get all dashboard count for admin
     *
     * @return DashboardCountResponse Dashboard Count Response
     * @throws BusinessException BusinessException
     * @throws SystemException   SystemException
     */
    Integer countAllActiveUserOrders() throws BusinessException, SystemException, ValidationException;
}
