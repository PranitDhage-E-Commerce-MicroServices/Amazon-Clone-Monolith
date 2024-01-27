package com.web.ecomm.app.service.impl;

import com.web.ecomm.app.repository.ProductReviewRepository;
import com.web.ecomm.app.pojo.ProductReview;
import com.web.ecomm.app.service.IProductReviewService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductReviewServiceImpl implements IProductReviewService {
    @Autowired
    ProductReviewRepository reviewRepo;

    @Override
    public List<ProductReview> getAllProductReviews(int prodId) {
            return reviewRepo.findAllByProductProdId(prodId);
    }

    @Override
    public double getAverageOfProductReview(int prodId) {
            return reviewRepo.getAverageOfProductReview(prodId);
    }

    @Override
    public ProductReview addNewProductReview(ProductReview review) {
        return reviewRepo.save(review);
    }
}
