package com.app.service;

import com.app.dao.CartRepository;
import com.app.exceptions.ResourceNotFoundException;
import com.app.pojo.Cart;
import com.app.utils.Constants;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements ICartService {

    @Autowired
    CartRepository cartRepo;

    @Override
    public List<Cart> getAllCartItems(int user_id) {
        return cartRepo.findAllByUserUserId(user_id);
    }

    @Override
    public Cart addCartItem(Cart item) {
        return cartRepo.save(item);
    }

    @Override
    public Cart updateCartItemQty(int cart_id, int item_qty) {
        Cart oldCart = cartRepo.findById(cart_id).orElseThrow(() -> new ResourceNotFoundException("Cart Item not found for given cart Id : " + cart_id, Constants.ERR_RESOURCE_NOT_FOUND));
            if (item_qty != oldCart.getCartQuantity()) oldCart.setCartQuantity(item_qty);
            return cartRepo.save(oldCart);
        }

    @Override
    public String deleteCartItem(int cart_id) {
        if (cartRepo.existsById(cart_id)) {
            cartRepo.deleteById(cart_id);
            return "Item deleted successfully";
        }
        throw new ResourceNotFoundException("Cart Item not found for given cart Id : " + cart_id, Constants.ERR_RESOURCE_NOT_FOUND);
    }

    @Override
    public String deleteAllCartItemByUser(int user_id) {
        cartRepo.deleteAllByUserUserId(user_id);
        return "Deleted All items from cart";
    }
}
