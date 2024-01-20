package com.app.service;

import com.app.dao.OrderDetailsRepository;
import com.app.pojo.OrderDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
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
