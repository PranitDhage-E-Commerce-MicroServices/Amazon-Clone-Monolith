package com.web.ecomm.app.service;

import com.web.ecomm.app.pojo.Myorder;

import java.util.List;

public interface IMyOrderService {
    List<Myorder> getMyOrderList(int userId);

    String checkoutMyOrder(Myorder myorder);

    Myorder updateMyOrderStatus(int myOrderId, String status);

    String deleteMyOrder(int myOrderId);

    List<Myorder> getAllUserOrders();

    String changeUserOrderDeliveryStatus(int myOrderId, String status);

    Integer countAllUserOrders();

    Integer countAllActiveUserOrders();
}
