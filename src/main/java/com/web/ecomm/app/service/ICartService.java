package com.web.ecomm.app.service;

import com.web.ecomm.app.pojo.Cart;

import java.util.List;

public interface ICartService {
    List<Cart> getAllCartItems(int userId);

    Cart addCartItem(Cart item);

    Cart updateCartItemQty(int cartId,  int quantity);

    boolean deleteCartItem(int cartId);

    boolean deleteAllCartItemByUser(int userId);
}
