package com.web.ecomm.app.service;

import com.web.ecomm.app.pojo.ProductReview;

import java.util.List;

public interface IProductReviewService {
    List<ProductReview> getAllProductReviews(int prodId);

    double getAverageOfProductReview(int prodId);

    ProductReview addNewProductReview(ProductReview review);
}
