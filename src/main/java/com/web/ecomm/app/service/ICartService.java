package com.web.ecomm.app.service;

import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.exceptions.ValidationException;
import com.web.ecomm.app.pojo.Cart;

import java.util.List;

public interface ICartService {

    /**
     * Get all the Card Items for given User
     *
     * @param userId User IDENTIFIER
     * @return List of Card items for given User Id
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     * @throws com.web.ecomm.app.exceptions.SystemException SystemException
     */
    List<Cart> getAllCartItems(int userId) throws BusinessException, SystemException;

    /**
     * Saves new Card Item for given user
     *
     * @param item Cart Item Request Body
     * @return Saved Card
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     * @throws com.web.ecomm.app.exceptions.SystemException SystemException
     */
    Cart addCartItem(Cart item) throws BusinessException, SystemException, ValidationException;

    /**
     * Updates Cart Item Quantity for given card Id
     *
     * @param cartId  Cart Identifier
     * @param quantity Cart Item Quantity
     * @return Updated Cart
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     * @throws com.web.ecomm.app.exceptions.SystemException SystemException
     */
    Cart updateCartItemQty(int cartId,  int quantity) throws BusinessException, SystemException, ValidationException;

    /**
     * Deletes Card Items for given Cart Id
     *
     * @param cartId Cart Identifier
     * @return Deleted Address Status
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     * @throws com.web.ecomm.app.exceptions.SystemException SystemException
     */
    boolean deleteCartItem(int cartId) throws BusinessException, SystemException;

    /**
     * Deletes All Card Items for given user Id
     *
     * @param userId User Identifier
     * @return Deleted Cart Item Status
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     * @throws com.web.ecomm.app.exceptions.SystemException SystemException
     */
    boolean deleteAllCartItemByUser(int userId) throws BusinessException, SystemException;
}
