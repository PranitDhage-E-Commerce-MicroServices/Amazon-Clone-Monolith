package com.web.ecomm.app.controller;

import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.models.DashboardCountResponse;
import com.web.ecomm.app.models.response.APIResponseEntity;
import com.web.ecomm.app.pojo.Myorder;
import com.web.ecomm.app.pojo.User;
import com.web.ecomm.app.service.ICategoryService;
import com.web.ecomm.app.service.ICompanyService;
import com.web.ecomm.app.service.IMyOrderService;
import com.web.ecomm.app.service.IProductService;
import com.web.ecomm.app.service.IUserService;
import com.web.ecomm.app.utils.Constants;
import com.web.ecomm.app.utils.Error;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    
    private IUserService userService;

    private IMyOrderService orderService;

    private IProductService productService;

    private ICompanyService companyService;

    private ICategoryService categoryService;

    @Autowired
    public AdminController(final IUserService userService,
                           final IMyOrderService orderService,
                           final IProductService productService,
                           final ICompanyService companyService,
                           final ICategoryService categoryService) {
        this.userService = userService;
        this.orderService = orderService;
        this.productService = productService;
        this.companyService = companyService;
        this.categoryService = categoryService;
    }

    /**
     * Get all the users for admin
     *
     * @return List of Users for admin
     * @throws BusinessException BusinessException
     * @throws SystemException   SystemException
     */
    @Operation(summary = "Get all the users for admin",
            description = "This API is used to get all the users for admin",
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
            value = "/user-list",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<List<User>>> getAllUserList()
            throws BusinessException, SystemException {

        log.info("Get all the users for admin");

        List<User> userList = userService.getUsersListAll();

        APIResponseEntity<List<User>> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                userList
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Change User Active Status
     *
     * @return Changed user active status
     * @throws BusinessException BusinessException
     * @throws SystemException   SystemException
     */
    @Operation(summary = "Change User Active Status",
            description = "This API is used to Change User Active Status",
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
            value = "/user-status/{userId}/{status}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<String>> changeUserActiveStatus(
            @PathVariable("userId") String userId,
            @PathVariable String status
    ) throws BusinessException, SystemException {

        log.info("Changing User Active Status for given User Id: {} and Status: {}", userId, status);

        APIResponseEntity<String> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                userService.changeUserActiveStatus(
                        Integer.parseInt(userId), Integer.parseInt(status)
                )
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get all user orders
     *
     * @return List of User Orders for admin
     * @throws BusinessException BusinessException
     * @throws SystemException SystemException
     */
    @Operation(summary = "Get all user orders",
            description = "This API is used to get all user orders",
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
            value = "/all-user-orders",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<List<Myorder>>> getAllUserOrders()
            throws BusinessException, SystemException {

        log.info("Getting all the user orders for admin");

        APIResponseEntity<List<Myorder>> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                orderService.getAllUserOrders()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Change user order delivery status for given MyOrder identifier
     *
     * @param myOrderId   Order Identifier
     * @param status Order Status
     * @return Updated Status
     * @throws BusinessException BusinessException
     * @throws SystemException SystemException
     */
    @Operation(summary = "Change user order delivery status for given MyOrder identifier",
            description = "This API is used to change user order delivery status",
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
            value = "/change-delivery-status/{myOrderId}/{status}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<String>> changeUserOrderDeliveryStatus(
            @Parameter(description = "My Oder Identifier", required = true)@PathVariable String myOrderId,
            @Parameter(description = "My Order Status", required = true)@PathVariable String status
    ) throws BusinessException, SystemException {

        log.info("Change user order delivery status status for given MyOrderId: {}, Status: {}", myOrderId, status);

        boolean updated = orderService.changeUserOrderDeliveryStatus(
                Integer.parseInt(myOrderId), status);
        APIResponseEntity<String> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                updated ? "Changed Delivery Status Successfully" : "Failed to Change Delivery Status"
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get all dashboard count for admin
     *
     * @return DashboardCountResponse Dashboard Count Response
     * @throws BusinessException BusinessException
     * @throws SystemException   SystemException
     */
    @Operation(summary = "Get all dashboard count for admin",
            description = "This API is used to get all dashboard count for admin",
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
            value = "/dashboard-count",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<DashboardCountResponse>> getAllDashboardCount()
            throws BusinessException, SystemException {

        DashboardCountResponse countResponse = new DashboardCountResponse();

        countResponse.setUserCount(userService.getAllUserCount());
        countResponse.setProductCount(productService.countAllProduct());
        countResponse.setMyOrderCount(orderService.countAllUserOrders());
        countResponse.setActiveOrderCount(orderService.countAllActiveUserOrders());
        countResponse.setCompanyCount(companyService.countAllCompany());
        countResponse.setCategoryCount(categoryService.countAllCategory());

        APIResponseEntity<DashboardCountResponse> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                countResponse
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
