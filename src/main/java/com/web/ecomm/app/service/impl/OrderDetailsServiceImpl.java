package com.web.ecomm.app.service.impl;

import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.pojo.OrderDetails;
import com.web.ecomm.app.repository.OrderDetailsRepository;
import com.web.ecomm.app.service.IOrderDetailsService;
import com.web.ecomm.app.utils.Constants;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
public class OrderDetailsServiceImpl implements IOrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepo;

    @Autowired
    public OrderDetailsServiceImpl(OrderDetailsRepository orderDetailsRepo) {
        this.orderDetailsRepo = orderDetailsRepo;
    }

    @Override
    public List<OrderDetails> getOrderDetailsList(int myOrderId)
            throws BusinessException, SystemException {

        try {
            return orderDetailsRepo.findAllByMyorderMyorderId(myOrderId);
        } catch (Exception e) {
            log.error("Exception While Getting All Order Details for Given MyOrder Id : {}, Message: {}", myOrderId, e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }
}
