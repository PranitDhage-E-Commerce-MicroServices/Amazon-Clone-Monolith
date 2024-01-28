package com.web.ecomm.app.service.impl;

import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.ResourceNotFoundException;
import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.exceptions.ValidationException;
import com.web.ecomm.app.pojo.Cart;
import com.web.ecomm.app.pojo.Myorder;
import com.web.ecomm.app.pojo.OrderDetails;
import com.web.ecomm.app.pojo.Products;
import com.web.ecomm.app.repository.CartRepository;
import com.web.ecomm.app.repository.MyOrderRepository;
import com.web.ecomm.app.repository.OrderDetailsRepository;
import com.web.ecomm.app.repository.ProductRepository;
import com.web.ecomm.app.service.IMyOrderService;
import com.web.ecomm.app.utils.Constants;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class MyOrderServiceImpl implements IMyOrderService {

    private final MyOrderRepository myOrderRepo;

    private final CartRepository cartRepo;

    private final OrderDetailsRepository orderDetailsRepo;

    private final ProductRepository productRepo;

    @Autowired
    public MyOrderServiceImpl(final MyOrderRepository myOrderRepo,
                              final CartRepository cartRepo,
                              final OrderDetailsRepository orderDetailsRepo,
                              final ProductRepository productRepo) {
        this.myOrderRepo = myOrderRepo;
        this.cartRepo = cartRepo;
        this.orderDetailsRepo = orderDetailsRepo;
        this.productRepo = productRepo;
    }


    @Override
    public List<Myorder> getMyOrderList(int userId)
            throws BusinessException, SystemException, ValidationException {

        try {
            return myOrderRepo.findAllByUserUserId(userId);
        } catch (Exception e) {
            log.error("Exception While Getting All MyOrder for Given User Id : {}, Message: {}", userId, e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public boolean checkoutMyOrder(Myorder myorder)
            throws BusinessException, SystemException, ValidationException {

        try {
            //Get Current User
            Integer userId = myorder.getUser().getUserId();

            //Get all cart items of current user
            List<Cart> cartItems = cartRepo.findAllByUserUserId(userId);

            //Calculate total amount
            double totalAmount = 0.0;
            for (Cart item : cartItems) {
                totalAmount += item.getProduct().getProdPrice() * item.getCartQuantity();
            }

            //Set total amount and tax to myOrder
            myorder.setTotalPrice((float) totalAmount);
            myorder.setTax((float) (totalAmount / 20));

            //Save myOrder and get reference
            Myorder myOrderSaved = myOrderRepo.save(myorder);

            //Create List of OrderDetails for saving all into OrderDetails
            List<OrderDetails> orderDetailsList = new ArrayList<>();

            //Get each item from cart and save its details into OrderDetails
            for (Cart cartItem : cartItems) {
                OrderDetails orderDetails = new OrderDetails(cartItem.getProduct().getProdPrice(), cartItem.getCartQuantity(),
                        (cartItem.getProduct().getProdPrice() * cartItem.getCartQuantity()), myOrderSaved, cartItem.getProduct());

                //Reduce Product Quantity by Purchased Quantity
                int prodId = cartItem.getProduct().getProdId();
                Products product = productRepo.findById(prodId).orElseThrow(() -> new ResourceNotFoundException("Product not found for given Product Id", Constants.ERR_RESOURCE_NOT_FOUND));
                product.setProdQty(product.getProdQty() - cartItem.getCartQuantity());
                productRepo.save(product);

                //Add all orderDetails into OrderDetailsList
                orderDetailsList.add(orderDetails);
            }

            //SaveAll orderDetailsList
            orderDetailsRepo.saveAll(orderDetailsList);

            //Empty cart for Current user
            cartRepo.deleteAllByUserUserId(userId);

            return true;

        } catch (Exception e) {
            log.error("Exception While Checking Out Order: {}", e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public Myorder updateMyOrderStatus(int myOrderId, String status)
            throws BusinessException, SystemException, ValidationException {

        try {
            Myorder myorder = myOrderRepo.findById(myOrderId)
                    .orElseThrow(
                            () -> new ResourceNotFoundException(
                                    "My Order not found for given myOrder Id : " + myOrderId,
                                    Constants.ERR_RESOURCE_NOT_FOUND)
                    );

            if (!StringUtils.equals(status, myorder.getDeliveryStatus())) {
                myorder.setDeliveryStatus(status);
                return myOrderRepo.save(myorder);
            }

            return myorder;
        } catch (ResourceNotFoundException e) {
            log.error("Exception While Updating MyOrder Status for Id: {} - {}", myOrderId, e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public boolean deleteMyOrder(int myOrderId)
            throws BusinessException, SystemException, ValidationException {

        try {
            if (!myOrderRepo.existsById(myOrderId)) {
                throw new ResourceNotFoundException(
                        "MyOrder not found for given MyOrder Id : " + myOrderId,
                        Constants.ERR_RESOURCE_NOT_FOUND);
            }

            myOrderRepo.deleteById(myOrderId);
            return true;
        } catch (ResourceNotFoundException e) {
            log.error("Exception While deleting MyOrder for Id: {} - {}", myOrderId, e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public List<Myorder> getAllUserOrders()
            throws BusinessException, SystemException, ValidationException {

        try {
            return myOrderRepo.findAllUserOrders();
        } catch (Exception e) {
            log.error("Exception While Getting All MyOrder - Message: {}", e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public boolean changeUserOrderDeliveryStatus(int myOrderId, String status)
            throws BusinessException, SystemException, ValidationException {

        try {
            Myorder myorder = myOrderRepo.findById(myOrderId)
                    .orElseThrow(
                            () -> new ResourceNotFoundException(
                                    "My Order not found for given myOrder Id : " + myOrderId,
                                    Constants.ERR_RESOURCE_NOT_FOUND)
                    );

            myorder.setDeliveryStatus(status);
            myOrderRepo.save(myorder);

            return true;
        } catch (ResourceNotFoundException e) {
            log.error("Exception While Updating Delivery Status for Id: {} - {}", myOrderId, e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public Integer countAllUserOrders()
            throws BusinessException, SystemException, ValidationException {

        try {
            return myOrderRepo.countAllUserOrders();
        } catch (Exception e) {
            log.error("Exception While getting all User Orders - {}", e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public Integer countAllActiveUserOrders()
            throws BusinessException, SystemException, ValidationException {

        try {
            return myOrderRepo.countAllActiveUserOrders();
        } catch (Exception e) {
            log.error("Exception While counting all active orders - {}", e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

}
