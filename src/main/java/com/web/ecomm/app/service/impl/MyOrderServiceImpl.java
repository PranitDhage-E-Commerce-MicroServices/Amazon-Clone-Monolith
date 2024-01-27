package com.web.ecomm.app.service.impl;

import com.web.ecomm.app.exceptions.ResourceNotFoundException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public List<Myorder> getMyOrderList(int userId) {
            return myOrderRepo.findAllByUserUserId(userId);
    }

    @Override
    public String checkoutMyOrder(Myorder myorder) {
        try {
            //Get Current User
            Integer user_id = myorder.getUser().getUserId();

            //Get all cart items of current user
            List<Cart> cartItems = cartRepo.findAllByUserUserId(user_id);

            //Calculate total amount
            double totalAmount = 0.0;
            for (Cart item: cartItems){
               totalAmount+=  item.getProduct().getProdPrice() * item.getCartQuantity();
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
            cartRepo.deleteAllByUserUserId(user_id);

            return "Order Placed Successfully";

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return "Error while placing order";
        }
    }

    @Override
    public Myorder updateMyOrderStatus(int myOrder_id, String status) {
            Myorder myorder = myOrderRepo.findById(myOrder_id).orElseThrow(() -> new ResourceNotFoundException("My Order not found for given myOrder Id : " + myOrder_id, Constants.ERR_RESOURCE_NOT_FOUND));
            if (status != myorder.getDeliveryStatus()) myorder.setDeliveryStatus(status);
            return myOrderRepo.save(myorder);
    }

    @Override
    public String deleteMyOrder(int myOrder_id) {
        if (myOrderRepo.existsById(myOrder_id)) {
            myOrderRepo.deleteById(myOrder_id);
            return "MyOrder deleted successfully";
        }
        throw new ResourceNotFoundException("My Order not found for given myOrder Id : " + myOrder_id, Constants.ERR_RESOURCE_NOT_FOUND);
    }

    @Override
    public List<Myorder> getAllUserOrders() {
        return myOrderRepo.findAllUserOrders();
    }

    @Override
    public String changeUserOrderDeliveryStatus(int myOrder_id, String status) {
            Myorder myorder = myOrderRepo.findById(myOrder_id).orElseThrow(() -> new ResourceNotFoundException("My Order not found for given myOrder Id : " + myOrder_id, Constants.ERR_RESOURCE_NOT_FOUND));
            myorder.setDeliveryStatus(status);
            myOrderRepo.save(myorder);
            return "User order delivery status changed successfully";
    }

    @Override
    public Integer countAllUserOrders() {
        return myOrderRepo.countAllUserOrders();
    }

    @Override
    public Integer countAllActiveUserOrders() {
        return myOrderRepo.countAllActiveUserOrders();
    }

}
