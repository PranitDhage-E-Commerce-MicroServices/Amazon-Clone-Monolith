package com.web.ecomm.app.service;

import com.web.ecomm.app.pojo.Cart;

import java.util.List;

public interface ICartService {
    List<Cart> getAllCartItems(int user_id);

    Cart addCartItem(Cart item);

    Cart updateCartItemQty(int cart_id,  int item_qty);

    String deleteCartItem(int cart_id);

    String deleteAllCartItemByUser(int user_id);
}
