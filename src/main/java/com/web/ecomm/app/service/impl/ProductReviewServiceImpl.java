package com.web.ecomm.app.service.impl;

import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.exceptions.ValidationException;
import com.web.ecomm.app.pojo.ProductReview;
import com.web.ecomm.app.repository.ProductReviewRepository;
import com.web.ecomm.app.service.IProductReviewService;
import com.web.ecomm.app.utils.Constants;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
public class ProductReviewServiceImpl implements IProductReviewService {

    private final ProductReviewRepository reviewRepo;

    @Autowired
    public ProductReviewServiceImpl(final ProductReviewRepository reviewRepo) {
        this.reviewRepo = reviewRepo;
    }

    @Override
    public List<ProductReview> getAllProductReviews(int prodId)
            throws BusinessException, ValidationException, SystemException {

        try {
            return reviewRepo.findAllByProductProdId(prodId);
        } catch (Exception e) {
            log.error("Exception While Getting All Product Reviews for Product Id : {}, Message: {}", prodId, e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public double getAverageOfProductReview(int prodId)
            throws BusinessException, ValidationException, SystemException {

        try {
            return reviewRepo.getAverageOfProductReview(prodId);
        } catch (Exception e) {
            log.error("Exception While Getting All Average Of Product Reviews for Product Id : {}, Message: {}", prodId, e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public ProductReview addNewProductReview(ProductReview review)
            throws BusinessException, ValidationException, SystemException {

        try {
            return reviewRepo.save(review);
        } catch (Exception e) {
            log.error("Exception While Saving Product Reviews : {}", e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }
}
