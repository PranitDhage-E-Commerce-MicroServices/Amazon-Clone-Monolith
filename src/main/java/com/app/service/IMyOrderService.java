package com.app.service;

import com.app.pojo.Myorder;

import java.util.List;

public interface IMyOrderService {
    List<Myorder> getMyOrderList(int user_Id);

    String checkoutMyOrder(Myorder myorder);

    Myorder updateMyOrderStatus(int myOrder_id, String status);

    String deleteMyOrder(int myOrder_id);

    List<Myorder> getAllUserOrders();

    String changeUserOrderDeliveryStatus(int myOrder_id, String status);

    Integer countAllUserOrders();

    Integer countAllActiveUserOrders();
}
