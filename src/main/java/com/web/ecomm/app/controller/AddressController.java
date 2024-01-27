package com.web.ecomm.app.controller;

import com.web.ecomm.app.models.response.APIResponseEntity;
import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.pojo.Address;
import com.web.ecomm.app.service.IAddressService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.ValidationException;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

    IAddressService addressService;

    @Autowired
    public AddressController(IAddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * Get all the addresses for given user
     *
     * @param userId User IDENTIFIER
     * @return List of Addresses for given User Id
     * @throws BusinessException BusinessException
     * @throws SystemException   SystemException
     */
    @Operation(summary = "Get all the addresses for given user",
            description = "This API is used to get all the addresses for the User with given userId",
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
    public ResponseEntity<APIResponseEntity<List<Address>>> getAllAddressList(
            @Parameter(description = "User Identifier", required = true) @PathVariable("userId") String userId,
            @Parameter(description = "Unique Request Id", required = false) @RequestHeader(required = false, value = Constants.REQ_ID_KEY) String reqId
    ) throws BusinessException, SystemException {

        List<Address> allAddresses = addressService.getAllAddresses(Integer.parseInt(userId));

        APIResponseEntity<List<Address>> APIResponseEntity =
                new APIResponseEntity<>(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, allAddresses);

        return new ResponseEntity<>(APIResponseEntity, HttpStatus.OK);
    }

    /**
     * Saves new address for given user
     *
     * @param address Address Request Body
     * @return Saved Address
     * @throws BusinessException BusinessException
     */
    @Operation(summary = "Saves new address for given user",
            description = "This API is used to save new address for the User with given userId",
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
    public APIResponseEntity<Address> addNewAddress(
            @Parameter(description = "Address request to be added", required = true) @RequestBody Address address
    ) throws BusinessException, ValidationException {

        Address add = addressService.addAddress(address);

        return new APIResponseEntity<>(
                Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, add
        );
    }

    /**
     * Updates address for given address Id
     *
     * @param addId   Address Identifier
     * @param address Address Request Body
     * @return Updated Address
     * @throws BusinessException BusinessException
     */
    @Operation(summary = "Updates address for given address Id",
            description = "This API is used to Updates address for given address Id",
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
            value = "/update/{add_id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public APIResponseEntity<Address> updateAddress(
            @Parameter(description = "Address Identifier", required = true) @PathVariable(value = "add_id") String addId,
            @Parameter(description = "Address request to be updated", required = true) @RequestBody Address address)
            throws BusinessException {

        log.info("Update address for given Address Id: {}, Request: {}", addId, address);

        Address add = addressService.updateAddress(Integer.parseInt(addId), address);

        return new APIResponseEntity<>(
                Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, add
        );

    }

    /**
     * Deletes address for given address Id
     *
     * @param addId Address Identifier
     * @return Deleted Address Status
     * @throws BusinessException BusinessException
     */
    @Operation(summary = "Deletes address for given address Id",
            description = "This API is used to delete address for given address Id",
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
            value = "/delete/{add_id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public APIResponseEntity<String> deleteAddress(
            @Parameter(description = "Address Identifier", required = true) @PathVariable(value = "add_id") String addId
    ) throws BusinessException {

        boolean deleted = addressService.deleteAddress(Integer.parseInt(addId));

        return new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                deleted ? "Address Deleted Successfully" : "Failed to delete Address"
        );
    }

}