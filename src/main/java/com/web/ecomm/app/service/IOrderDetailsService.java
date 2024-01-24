package com.web.ecomm.app.service;

import com.web.ecomm.app.pojo.OrderDetails;

import java.util.List;

public interface IOrderDetailsService {
    List<OrderDetails> getOrderDetailsList(int myorder_id);

}
