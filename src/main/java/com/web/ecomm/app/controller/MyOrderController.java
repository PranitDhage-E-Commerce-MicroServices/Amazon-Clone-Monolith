package com.web.ecomm.app.controller;

import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.exceptions.ValidationException;
import com.web.ecomm.app.models.response.APIResponseEntity;
import com.web.ecomm.app.pojo.Myorder;
import com.web.ecomm.app.service.IMyOrderService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/my-order")
public class MyOrderController {
    
    private final IMyOrderService myOrderService;

    @Autowired
    public MyOrderController(IMyOrderService myOrderService) {
        this.myOrderService = myOrderService;
    }

    /**
     * Get all the User Order List for given user
     *
     * @return List of User Order for given User Id
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.SystemException   SystemException
     */
    @Operation(summary = "Get all the User Order List for given user",
            description = "This API is used to Get all the User Order List for given user",
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
            value = "/list",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<List<Myorder>>> getAllUserOrderList()
            throws BusinessException, SystemException {

        log.info("Getting all user Order list");

        APIResponseEntity<List<Myorder>> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                myOrderService.getAllUserOrders()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * Get all the MyOrder for given user Id
     *
     * @param userId User IDENTIFIER
     * @return List of MyOrder for given User Id
     * @throws BusinessException BusinessException
     * @throws SystemException   SystemException
     */
    @Operation(summary = "Get all the MyOrder for given user",
            description = "This API is used to get all the MyOrder for the User with given userId",
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
            value = "/list/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<List<Myorder>>> getMyOrderList(
            @Parameter(description = "User Identifier", required = true) @PathVariable("userId") String userId
    ) throws BusinessException, SystemException {

        log.info("Get all myOrder list for Given User Id: {}", userId);

        List<Myorder> myOrderList = myOrderService.getMyOrderList(Integer.parseInt(userId));

        APIResponseEntity<List<Myorder>> response =
                new APIResponseEntity<>(
                        Constants.STATUS_SUCCESS,
                        Constants.SUCCESS_CODE,
                        myOrderList
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Checkout My Order for given user
     *
     * @param myorder MyOrder Request Body
     * @return Saved Address
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     * @throws com.web.ecomm.app.exceptions.SystemException SystemException
     */
    @Operation(summary = "Checkout My Order for given user",
            description = "This API is used to Checkout My Order for given user",
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
            value = "/checkout",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<String>> checkoutMyOrder(
            @Parameter(description = "MyOrder request to be added", required = true) @RequestBody Myorder myorder
    ) throws BusinessException, ValidationException, SystemException {

        log.info("Checkout My Order for given user Request: {}", myorder);

        boolean checkedOut = myOrderService.checkoutMyOrder(myorder);

        APIResponseEntity<String> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                checkedOut ? "Order Placed Successfully" : "Error while placing order"
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Updates MyOrder Status for given MyOrder Id
     *
     * @param myOrderId MyOrder Identifier
     * @param status MyOrder Status
     * @return Updated MyOrder
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     * @throws com.web.ecomm.app.exceptions.SystemException SystemException
     */
    @Operation(summary = "Updates MyOrder Status for given MyOrder Id",
            description = "This API is used to Updates MyOrder Status for given MyOrder Id",
            tags = {"Checks"},
            method = "PUT"
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
    @PutMapping(
            value = "/update/{myOrderId}/{status}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<Myorder>> updateMyOrderStatus(
            @Parameter(description = "MyOrder Identifier", required = true) @PathVariable("myOrderId") String myOrderId,
            @Parameter(description = "MyOrder Status", required = true) @PathVariable("status") String status
    ) throws BusinessException, SystemException, ValidationException {

        log.info("Update myOrder status for given Id: {}, Status: {}", myOrderId, status);

        Myorder myorder = myOrderService.updateMyOrderStatus(Integer.parseInt(myOrderId), status);

        APIResponseEntity<Myorder> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                myorder
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Deletes MyOrder for given MyOrder Id
     *
     * @param myOrderId MyOrder Identifier
     * @return Deleted MyOrder Status
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.SystemException SystemException
     */
    @Operation(summary = "Deletes MyOrder for given MyOrder Id",
            description = "This API is used to delete MyOrder for given MyOrder Id",
            tags = {"Checks"},
            method = "DELETE"
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
    @DeleteMapping(
            value = "/delete/{myOrderId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<String>> deleteMyOrder(
            @Parameter(description = "MyOrder Identifier", required = true) @PathVariable("myOrderId") String myOrderId
    ) throws BusinessException, SystemException {
        log.info("Deleting MyOrder for given MyOrder Id: {}", myOrderId);

        boolean deleted = myOrderService.deleteMyOrder(Integer.parseInt(myOrderId));
        APIResponseEntity<String> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                deleted ? "MyOrder Deleted Successfully" : "Failed to delete MyOrder"
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
