package com.web.ecomm.app.service.impl;

import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.ResourceNotFoundException;
import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.exceptions.ValidationException;
import com.web.ecomm.app.pojo.Cart;
import com.web.ecomm.app.repository.CartRepository;
import com.web.ecomm.app.service.ICartService;
import com.web.ecomm.app.utils.Constants;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
public class CartServiceImpl implements ICartService {

    private final CartRepository cartRepo;

    @Autowired
    public CartServiceImpl(final CartRepository cartRepo) {
        this.cartRepo = cartRepo;
    }

    @Override
    public List<Cart> getAllCartItems(int userId)
            throws BusinessException, SystemException, ValidationException {

        try {
            return cartRepo.findAllByUserUserId(userId);
        } catch (Exception e) {
            log.error("Exception While Getting All Cart Items for Given User Id : {}, Message: {}", userId, e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public Cart addCartItem(Cart item)
            throws BusinessException, SystemException, ValidationException {

        try {
            return cartRepo.save(item);
        } catch (Exception e) {
            log.error("Exception While Saving Cart Item: {}", e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public Cart updateCartItemQty(int cartId, int itemQty)
            throws BusinessException, SystemException, ValidationException {

        try {

            Cart oldCart = cartRepo.findById(cartId)
                    .orElseThrow(
                            () -> new ResourceNotFoundException(
                                    "Cart Item not found for given cart Id : " + cartId,
                                    Constants.ERR_RESOURCE_NOT_FOUND
                            )
                    );

            if (itemQty != oldCart.getCartQuantity()) {
                oldCart.setCartQuantity(itemQty);
                return cartRepo.save(oldCart);
            }
            return oldCart;
        } catch (ResourceNotFoundException e) {
            log.error("Exception While Updating Cart Item Quantity for Cart Item: {}, {}", cartId, e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public boolean deleteCartItem(int cartId)
            throws BusinessException, SystemException, ValidationException {

        try {
            if (!cartRepo.existsById(cartId)) {
                throw new ResourceNotFoundException(
                        "Cart Item not found for given cart Id : " + cartId,
                        Constants.ERR_RESOURCE_NOT_FOUND);
            }

            cartRepo.deleteById(cartId);
            return true;
        } catch (ResourceNotFoundException e) {
            log.error("Exception While deleting Cart Item for Id: {} - {}", cartId, e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public boolean deleteAllCartItemByUser(int userId)
            throws BusinessException, SystemException, ValidationException {

        try {
            cartRepo.deleteAllByUserUserId(userId);
            return true;
        } catch (Exception e) {
            log.error("Exception While deleting all Cart Item for User Id: {} - {}", userId, e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }
}
