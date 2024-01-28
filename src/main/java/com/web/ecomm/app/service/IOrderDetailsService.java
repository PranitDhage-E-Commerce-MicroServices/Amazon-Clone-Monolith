package com.web.ecomm.app.service;

import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.pojo.OrderDetails;

import java.util.List;

public interface IOrderDetailsService {

    /**
     * Get Order Details List for given Order id
     *
     * @param myOrderId MyOrder IDENTIFIER
     * @return List of Order Details for given MyOrder Id
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.SystemException   SystemException
     */
    List<OrderDetails> getOrderDetailsList(int myOrderId) throws BusinessException, SystemException;

}
