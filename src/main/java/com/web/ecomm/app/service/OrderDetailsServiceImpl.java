package com.web.ecomm.app.service;

import com.web.ecomm.app.dao.OrderDetailsRepository;
import com.web.ecomm.app.pojo.OrderDetails;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OrderDetailsServiceImpl implements IOrderDetailsService {

    @Autowired
    OrderDetailsRepository orderDetailsRepo;

    @Override
    public List<OrderDetails> getOrderDetailsList(int myorder_id) {
        return orderDetailsRepo.findAllByMyorderMyorderId(myorder_id);
    }


}
