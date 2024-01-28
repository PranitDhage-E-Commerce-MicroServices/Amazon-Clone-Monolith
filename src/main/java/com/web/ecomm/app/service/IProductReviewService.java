package com.web.ecomm.app.service;

import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.exceptions.ValidationException;
import com.web.ecomm.app.pojo.ProductReview;

import java.util.List;

public interface IProductReviewService {

    /**
     * Get all the Product Review List for given Product Id
     *
     * @param prodId Product Identifier
     * @return List of Products Reviews for Product Id
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.SystemException   SystemException
     */
    List<ProductReview> getAllProductReviews(int prodId) throws BusinessException, SystemException;

    /**
     * Get all the Product Review Average for given Product Id
     *
     * @param prodId Product Identifier
     * @return List of Products Review Average for Product Id
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.SystemException   SystemException
     */
    double getAverageOfProductReview(int prodId) throws BusinessException, SystemException;

    /**
     * Saves new Review for given user
     *
     * @param review Review Request Body
     * @return Saved Review
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     * @throws com.web.ecomm.app.exceptions.SystemException SystemException
     */
    ProductReview addNewProductReview(ProductReview review) throws BusinessException, ValidationException, SystemException;
}
