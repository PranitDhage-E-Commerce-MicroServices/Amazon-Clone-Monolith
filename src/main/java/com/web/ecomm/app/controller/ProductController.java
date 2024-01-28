package com.web.ecomm.app.controller;

import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.exceptions.ValidationException;
import com.web.ecomm.app.models.response.APIResponseEntity;
import com.web.ecomm.app.pojo.Products;
import com.web.ecomm.app.service.IProductService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/product")
public class ProductController {

    private final IProductService productService;

    @Autowired
    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    /**
     * Get all the Product List for Admin
     *
     * @return List of Products for Admin
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.SystemException   SystemException
     */
    @Operation(summary = "Get all the Product List for Admin",
            description = "This API is used to Get all the Product List for Admin",
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
    public ResponseEntity<APIResponseEntity<List<Products>>> getAllProductList()
            throws BusinessException, SystemException {

        log.info("Getting all the Product List for Admin");

        APIResponseEntity<List<Products>> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                productService.getAllProducts()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get all the Gallery Product List
     *
     * @return List of Gallery Products
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.SystemException   SystemException
     */
    @Operation(summary = "Get all the Gallery Product List",
            description = "This API is used to Get all the Gallery Product List",
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
            value = "/gallery-list",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<List<Products>>> getGalleryProductList()
            throws BusinessException, SystemException {

        log.info("Getting Gallery Product list");

        APIResponseEntity<List<Products>> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                productService.getGalleryProducts()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get all the Product Details for given Product Id
     *
     * @return Product Details for Given Product Id
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.SystemException   SystemException
     */
    @Operation(summary = "Get all the Product Details for given Product Id",
            description = "This API is used to Get all the Product Details for given Product Id",
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
            value = "/details/{prodId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<Products>> getProduct(
            @Parameter(description = "Product Identifier", required = true) @PathVariable("prodId") String prodId
    ) throws BusinessException, SystemException {

        log.info("Getting all the Product Details for given Product Id: {}", prodId);

        Products product = productService.getProductDetails(Integer.parseInt(prodId));

        APIResponseEntity<Products> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                product
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Saves new Product for Admin
     *
     * @param product Product Request Body
     * @return Saved Product
     * @throws com.web.ecomm.app.exceptions.BusinessException   BusinessException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     * @throws com.web.ecomm.app.exceptions.SystemException     SystemException
     */
    @Operation(summary = "Saves new Product for Admin",
            description = "This API is used to Saves new Product for Admin",
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
    public ResponseEntity<APIResponseEntity<Products>> addNewProduct(
            @Parameter(description = "Product request to be added", required = true) @RequestBody Products product
    ) throws BusinessException, ValidationException, SystemException {

        log.info("Adding new Product Request: " + product);

        Products prod = productService.addProducts(product);

        APIResponseEntity<Products> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                prod);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Updates Product for Given Product Id for Admin
     *
     * @param product Product Request Body
     * @param prodId  Product Identifier
     * @return Updated Product
     * @throws com.web.ecomm.app.exceptions.BusinessException   BusinessException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     * @throws com.web.ecomm.app.exceptions.SystemException     SystemException
     */
    @Operation(summary = "Updates Product for Given Product Id for Admin",
            description = "This API is used to Updates Product for Given Product Id for Admin",
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
            value = "/update/{prodId}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<Products>> updateProduct(
            @Parameter(description = "Product request to be updated", required = true) @RequestBody Products product,
            @Parameter(description = "Product Identifier", required = true) @PathVariable("prodId") String prodId
    ) throws BusinessException, ValidationException, SystemException {

        log.info("Updating Product For Product Id: {}, Request: {}", prodId, product);

        Products prod = productService.updateProducts(Integer.parseInt(prodId), product);

        APIResponseEntity<Products> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                prod);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Deletes Product for given Product Id
     *
     * @param prodId Product Identifier
     * @return Deleted Product Status
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.SystemException   SystemException
     */
    @Operation(summary = "Deletes Product for given Product Id",
            description = "This API is used to delete Product for given Product Id",
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
            value = "/delete/{prodId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<String>> deleteProduct(
            @Parameter(description = "Product Identifier", required = true) @PathVariable("prodId") String prodId
    ) throws IOException, BusinessException, SystemException {

        log.info("Deleting Product for given Product Id: {}", prodId);

        boolean deleted = productService.deleteProducts(Integer.parseInt(prodId));

        APIResponseEntity<String> response = new APIResponseEntity(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                deleted ? "Product Deleted Successfully" : "Failed to delete Product"
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Changes Product Active Status for Given Product Id for Admin
     *
     * @param status Product Active Status
     * @param prodId Product Identifier
     * @return Updated Product Status
     * @throws com.web.ecomm.app.exceptions.BusinessException   BusinessException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     * @throws com.web.ecomm.app.exceptions.SystemException     SystemException
     */
    @Operation(summary = "Changes Product Active Status for Given Product Id for Admin",
            description = "This API is used to Changes Product Active Status for Given Product Id for Admin",
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
            value = "/change-active-status/{prodId}/{status}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<String>> changeProductActiveStatus(
            @Parameter(description = "Product Identifier", required = true) @PathVariable String prodId,
            @Parameter(description = "Product Status", required = true) @PathVariable String status
    ) throws BusinessException, SystemException {

        log.info("Changing Product Active Status For Product Id: {}, Status: {}", prodId, status);

        boolean statusChanged = productService.changeProductActiveStatus(
                Integer.parseInt(prodId), Integer.parseInt(status)
        );

        APIResponseEntity<String> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                statusChanged ? "Active Status Changed Successfully" : "Active Status Could Not Be Changed"
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get Product Image for given Photo Name
     *
     * @param photo Photo IDENTIFIER
     * @return Image for given Photo Id
     * @throws BusinessException BusinessException
     * @throws SystemException   SystemException
     */
    @Operation(summary = "Get Product Image for given Photo Name",
            description = "This API is used to Get Product Image for given Photo Name",
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
            value = "/get-image/{photo}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<byte[]>> getProductImage(
            @Parameter(description = "Photo Identifier", required = true) @PathVariable String photo
    ) throws IOException, BusinessException, SystemException {

        log.info("Getting product image for given photo: {}", photo);

        byte[] image = productService.getPhotoByName(photo);

        APIResponseEntity<byte[]> response =
                new APIResponseEntity<>(
                        Constants.STATUS_SUCCESS,
                        Constants.SUCCESS_CODE,
                        image);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Upload Product Image for Given Product Id for Admin
     *
     * @param multipartFile Product Image Status
     * @param prodId        Product Identifier
     * @return Updated Product Status
     * @throws com.web.ecomm.app.exceptions.BusinessException   BusinessException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     * @throws com.web.ecomm.app.exceptions.SystemException     SystemException
     */
    @Operation(summary = "Upload Product Image for Given Product Id for Admin",
            description = "This API is used to Upload Product Image for Given Product Id for Admin",
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
            value = "/upload-image/{prodId}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<String>> uploadProductImage(
            @PathVariable("prodId") String prodId,
            @RequestParam("productImage") MultipartFile multipartFile
    ) throws IOException, BusinessException, SystemException {

        log.info("in upload product image");

        boolean uploaded = productService.uploadProductImage(Integer.parseInt(prodId), multipartFile);

        APIResponseEntity<String> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                uploaded ? "Product Image Uploaded Successfully" : "Failed to Upload Product Image"
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Deletes Product Image for given Product Id
     *
     * @param prodId Product Identifier
     * @return Deleted Product Image Status
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.SystemException   SystemException
     */
    @Operation(summary = "Deletes Product Image for given Product Id",
            description = "This API is used to delete Product Image for given Product Id",
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
            value = "/image-delete/{prodId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<String>> deleteProductImage(
            @Parameter(description = "Product Identifier", required = true) @PathVariable String prodId
    ) throws IOException, BusinessException, SystemException {

        log.info("Deleting product image for given Product Id: {}", prodId);

        boolean deleted = productService.deleteProductImage(Integer.parseInt(prodId));

        APIResponseEntity<String> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                deleted ? "Product Image Deleted Successfully" : "Failed to Delete Product Image, Product image is not available"
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
