package com.web.ecomm.app.controller;

import com.web.ecomm.app.models.response.APIResponseEntity;
import com.web.ecomm.app.exceptions.UnexpectedErrorException;
import com.web.ecomm.app.pojo.ProductReview;
import com.web.ecomm.app.service.IProductReviewService;
import com.web.ecomm.app.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/review")
public class ProductReviewController {
    @Autowired
    IProductReviewService reviewService;

    public ProductReviewController() {
        System.out.println("in ProductReviewController -- " + getClass().getName());
    }

    @GetMapping("/list/{prod_id}")/*-------------------------------------------------------User getAllProductReviewList Done------------------------------------------------ */
    public APIResponseEntity getAllProductReviewList(@PathVariable String prod_id) {
        System.out.println("in get all product review list");
            return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, reviewService.getAllProductReviews(Integer.parseInt(prod_id)));
    }

    @GetMapping("/average/{prod_id}")/*-------------------------------------------------------User getAverageProductRating Done------------------------------------------------ */
    public APIResponseEntity getAverageProductRating(@PathVariable String prod_id) {
        System.out.println("in get average product rating");
        double avgRating = reviewService.getAverageOfProductReview(Integer.parseInt(prod_id));
        if (avgRating != 0) {
            return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, avgRating);
        }
        throw new UnexpectedErrorException(" Average  product review not found for given product", Constants.ERR_RESOURCE_NOT_FOUND);
    }

    @PostMapping("/add")/*-------------------------------------------------------User addNewReview Done------------------------------------------------ */
    public APIResponseEntity addNewReview(@RequestBody ProductReview review) {
        System.out.println("in add new review");
        ProductReview productReview = reviewService.addNewProductReview(review);
        if (productReview != null) {
            return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, "product Review added successfully");
        }
        throw new UnexpectedErrorException("Error while adding new  product Review", Constants.ERR_DEFAULT);
    }

}
