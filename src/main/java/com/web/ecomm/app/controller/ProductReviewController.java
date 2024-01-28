package com.web.ecomm.app.controller;

import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.models.response.APIResponseEntity;
import com.web.ecomm.app.pojo.ProductReview;
import com.web.ecomm.app.service.IProductReviewService;
import com.web.ecomm.app.utils.Constants;
import com.web.ecomm.app.utils.Error;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/review")
public class ProductReviewController {
    
    private final IProductReviewService reviewService;

    @Autowired
    public ProductReviewController(IProductReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /**
     * Get all the Product Review List for given Product Id
     *
     * @param prodId Product Identifier
     * @return List of Products Reviews for Product Id
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.SystemException   SystemException
     */
    @Operation(summary = "Get all the Product Review List for given Product Id",
            description = "This API is used to Get all the Product Review List for given Product Id",
            tags = {"Checks"},
            method = "GET"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Unexpected Error", content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "403", description = "Access Denied - User is either invalid or is not entitled to requested api action", content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "404", description = "Entity Not Found", content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Error.class)))
            }
    )
    @GetMapping(
            value = "/list/{prodId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<List<ProductReview>>> getAllProductReviewList(
            @Parameter(description = "Product Identifier", required = true) @PathVariable("prodId") String prodId
    ) throws BusinessException, SystemException {

        log.info("Getting all the Product Review List for given Product Id: {}", prodId);

        List<ProductReview> productReviews = reviewService.getAllProductReviews(Integer.parseInt(prodId));

        APIResponseEntity<List<ProductReview>> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                productReviews
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get all the Product Review Average for given Product Id
     *
     * @param prodId Product Identifier
     * @return List of Products Review Average for Product Id
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.SystemException   SystemException
     */
    @Operation(summary = "Get all the Product Review Average for given Product Id",
            description = "This API is used to Get all the Product Review Average for given Product Id",
            tags = {"Checks"},
            method = "GET"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Unexpected Error", content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "403", description = "Access Denied - User is either invalid or is not entitled to requested api action", content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "404", description = "Entity Not Found", content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Error.class)))
            }
    )
    @GetMapping(
            value = "/average/{prodId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<Double>> getAverageProductRating(
            @Parameter(description = "Product Identifier", required = true) @PathVariable("prodId") String prodId
    ) throws BusinessException, SystemException {

        log.info(" Get all the Product Review Average for given Product Id: {}", prodId);

        double avgRating = reviewService.getAverageOfProductReview(Integer.parseInt(prodId));

        APIResponseEntity<Double> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                avgRating
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Saves new Review for given user
     *
     * @param review Review Request Body
     * @return Saved Review
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     * @throws com.web.ecomm.app.exceptions.SystemException SystemException
     */
    @Operation(summary = "Saves new Review for given user",
            description = "This API is used to save new Review for the User",
            tags = {"Checks"},
            method = "POST"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Unexpected Error", content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "403", description = "Access Denied - User is either invalid or is not entitled to requested api action", content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "404", description = "Entity Not Found", content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Error.class)))
            }
    )
    @PostMapping(
            value = "/add",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<ProductReview>> addNewReview(
            @RequestBody ProductReview review
    ) throws BusinessException, SystemException {

        log.info("Adding new Product Review Request: {}", review);

        ProductReview productReview = reviewService.addNewProductReview(review);

        APIResponseEntity<ProductReview> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                productReview
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
