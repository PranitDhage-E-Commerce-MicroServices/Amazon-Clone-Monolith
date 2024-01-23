package com.app.controller;

import com.app.dto.ResponseDTO;
import com.app.exceptions.UnexpectedErrorException;
import com.app.pojo.ProductReview;
import com.app.service.IProductReviewService;
import com.app.utils.Constants;
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
    public ResponseDTO getAllProductReviewList(@PathVariable String prod_id) {
        System.out.println("in get all product review list");
            return new ResponseDTO(true, reviewService.getAllProductReviews(Integer.parseInt(prod_id)));
    }

    @GetMapping("/average/{prod_id}")/*-------------------------------------------------------User getAverageProductRating Done------------------------------------------------ */
    public ResponseDTO getAverageProductRating(@PathVariable String prod_id) {
        System.out.println("in get average product rating");
        double avgRating = reviewService.getAverageOfProductReview(Integer.parseInt(prod_id));
        if (avgRating != 0) {
            return new ResponseDTO(true, avgRating);
        }
        throw new UnexpectedErrorException(" Average  product review not found for given product", Constants.ERR_RESOURCE_NOT_FOUND);
    }

    @PostMapping("/add")/*-------------------------------------------------------User addNewReview Done------------------------------------------------ */
    public ResponseDTO addNewReview(@RequestBody ProductReview review) {
        System.out.println("in add new review");
        ProductReview productReview = reviewService.addNewProductReview(review);
        if (productReview != null) {
            return new ResponseDTO(true, "product Review added successfully");
        }
        throw new UnexpectedErrorException("Error while adding new  product Review", Constants.ERR_DEFAULT);
    }

}
