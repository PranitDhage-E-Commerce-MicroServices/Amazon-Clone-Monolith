package com.web.ecomm.app.controller;

import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.models.response.APIResponseEntity;
import com.web.ecomm.app.pojo.Category;
import com.web.ecomm.app.service.ICategoryService;
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
@RequestMapping("/category")
public class CategoryController {
    private final ICategoryService categoryService;

    @Autowired
    public CategoryController(final ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Get all the Categories for Admin
     *
     * @return List of Categories for Admin
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.SystemException SystemException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     */
    @Operation(summary = "Get all the Categories for Admin",
            description = "This API is used to get all the Categories for Admin",
            tags = {"Category"},
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
    public ResponseEntity<APIResponseEntity<List<Category>>> getAllCategoryList()
            throws BusinessException, SystemException {

        log.info("Getting all category list for Admin");

        APIResponseEntity<List<Category>> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                categoryService.getAllCategories()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get Category Details By Id for Admin
     *
     * @return List of Category Details By Id for Admin
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.SystemException SystemException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     */
    @Operation(summary = "Get Category Details By Id for Admin",
            description = "This API is used to get Category Details By Id for Admin",
            tags = {"Category"},
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
            value = "/details/{catId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<Category>> getCategoryDetailsById(
            @Parameter(description = "Category Identifier", required = true) @PathVariable String catId
    ) throws BusinessException, SystemException {

        log.info("Getting category details by Category Id: {}", catId);

        Category category = categoryService.getCategoryDetailsById(Integer.parseInt(catId));

        APIResponseEntity<Category> response =
                new APIResponseEntity<>(
                        Constants.STATUS_SUCCESS,
                        Constants.SUCCESS_CODE,
                        category);

        return new ResponseEntity<>(response, HttpStatus.OK);
        }

    /**
     * Saves new Category by Admin
     *
     * @param category Category Request Body
     * @return Saved Category
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     * @throws com.web.ecomm.app.exceptions.SystemException SystemException
     */
    @Operation(summary = "Saves new category for Admin",
            description = "This API is used to save new category for Admin",
            tags = {"Category"},
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
    public ResponseEntity<APIResponseEntity<Category>> addNewCategory(
            @Parameter(description = "Category request to be added", required = true) @RequestBody Category category
    ) throws BusinessException, ValidationException, SystemException {

        log.info("Adding new category Request: {}", category);

        Category cat = categoryService.addCategory(category);

        APIResponseEntity<Category> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                category
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Updates Category for given Category Id
     *
     * @param catId Category Identifier
     * @param category Category Request Body
     * @return Updated Category
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     * @throws com.web.ecomm.app.exceptions.SystemException SystemException
     */
    @Operation(summary = "Updates Category for given Category Id",
            description = "This API is used to Updates Category for given Category Id",
            tags = {"Category"},
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
            value = "/update/{catId}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<Category>> updateCategory(
            @Parameter(description = "Category request to be updated", required = true) @RequestBody Category category,
            @Parameter(description = "Category Identifier", required = true) @PathVariable("catId") String catId
    ) throws BusinessException, ValidationException, SystemException {

        log.info("Updating new category Request: {}", category);

        Category cat = categoryService.updateCategory(Integer.parseInt(catId), category);

        APIResponseEntity<Category> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                cat
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Deletes Category for given Category Id
     *
     * @param catId Category Identifier
     * @return Deleted Category Status
     * @throws BusinessException BusinessException
     */
    @Operation(summary = "Deletes Category for given Category Id",
            description = "This API is used to delete Category for given Category Id",
            tags = {"Category"},
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
            value = "/delete/{catId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponseEntity<String>> deleteCategory(
            @Parameter(description = "Category Identifier", required = true) @PathVariable("catId") String catId
    ) throws BusinessException, SystemException {

        log.info("Deleting category details by Category Id: {}", catId);

        boolean deleted = categoryService.deleteCategory(Integer.parseInt(catId));

        APIResponseEntity<String> response = new APIResponseEntity<>(
                Constants.STATUS_SUCCESS,
                Constants.SUCCESS_CODE,
                deleted ? "Category Deleted Successfully" : "Failed to delete Category"
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
