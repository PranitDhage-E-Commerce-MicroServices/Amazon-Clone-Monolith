package com.web.ecomm.app.controller;

import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.models.response.APIResponseEntity;
import com.web.ecomm.app.pojo.Cart;
import com.web.ecomm.app.service.ICartService;
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

import javax.xml.bind.ValidationException;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/cart")
public class CartController {

    private final ICartService cartService;

    @Autowired
    public CartController(final ICartService cartService) {
        this.cartService = cartService;
    }

    /**
     * Get all the Card Items for given User
     *
     * @param userId User IDENTIFIER
     * @return List of Card items for given User Id
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.SystemException   SystemException
     */
    @Operation(summary = "Get all the Card Items for given User",
            description = "This API is used to get all the Card Items for given User",
            tags = {"Cart"},
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
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<List<Cart>>> getAllCartItemList(
            @Parameter(description = "User Identifier", required = true) @PathVariable String userId
    ) throws BusinessException, SystemException {

        log.info("Getting all cart items for User Id: {}", userId);

        APIResponseEntity<List<Cart>> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                cartService.getAllCartItems(Integer.parseInt(userId))
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Saves new Card Item for given user
     *
     * @param cartItem Card Request Body
     * @return Saved Card
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.SystemException   SystemException
     */
    @Operation(summary = "Saves new address for given user",
            description = "This API is used to save new address for the User with given userId",
            tags = {"Cart"},
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
    public ResponseEntity<APIResponseEntity<Cart>> addNewCartIem(
            @Parameter(description = "Card request to be added", required = true) @RequestBody Cart cartItem
    ) throws BusinessException, ValidationException, SystemException {

        // Validate card Item
        log.info("Adding new cart item: {}", cartItem);

        Cart cart = cartService.addCartItem(cartItem);

        APIResponseEntity<Cart> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                cart
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

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
    @Operation(summary = "Updates Cart Item Quantity for given card Id",
            description = "This API is used to Updates Cart Item Quantity for given card Id",
            tags = {"Cart"},
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
            value = "/update/{cartId}/{quantity}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<Cart>> updateCartItemQty(
            @Parameter(description = "Card Identifier", required = true) @PathVariable String cartId,
            @Parameter(description = "Card Item Quantity", required = true) @PathVariable String quantity
    ) throws BusinessException, SystemException {

        log.info("Updating cart item quantity for Card Id: {}, Quantity: {}", cartId, quantity);

        Cart cart = cartService.updateCartItemQty(
                Integer.parseInt(cartId), Integer.parseInt(quantity)
        );

        log.info("Updated cart item quantity for Product: {}, Quantity: {}", cart.getProduct().getProdTitle(), quantity);

        APIResponseEntity<Cart> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                cart
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Deletes Card Items for given Cart Id
     *
     * @param cartId Cart Identifier
     * @return Deleted Address Status
     * @throws BusinessException BusinessException
     * @throws SystemException SystemException
     */
    @Operation(summary = "Deletes Card Items for given Cart Id",
            description = "This API is used to deletes Card Items for given Cart Id",
            tags = {"Cart"},
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
            value = "/delete/{cartId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<String>> deleteCartItem(
            @Parameter(description = "Card Identifier", required = true) @PathVariable String cartId
    ) throws BusinessException, SystemException {

        log.info("Deleting  cart item for given Cart Id: {}", cartId);

        boolean deleted = cartService.deleteCartItem(Integer.parseInt(cartId));
        APIResponseEntity<String> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                deleted ? "Cart Item Deleted Successfully" : "Failed to Delete Cart Item"
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Deletes All Card Items for given user Id
     *
     * @param userId User Identifier
     * @return Deleted Cart Item Status
     * @throws BusinessException BusinessException
     * @throws SystemException SystemException
     */
    @Operation(summary = "Deletes All Card Items for given user Id",
            description = "This API is used to deletes all Card Items for given user Id",
            tags = {"Cart"},
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
            value = "/deleteAll/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<String>> deleteAllCartItemsByUser(
            @Parameter(description = "User Identifier", required = true) @PathVariable("userId") String userId
    ) throws BusinessException, SystemException {

        log.info("Deleting all cart item for given User Id: {}", userId);

        boolean deleted = cartService.deleteAllCartItemByUser(Integer.parseInt(userId));

        APIResponseEntity<String> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                deleted ? "All Cart Item Deleted Successfully" : "Failed to Delete All Cart Item"
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
