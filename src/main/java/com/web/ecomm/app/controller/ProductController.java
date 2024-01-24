package com.web.ecomm.app.controller;

import com.web.ecomm.app.dto.APIResponseEntity;
import com.web.ecomm.app.exceptions.ResourceNotFoundException;
import com.web.ecomm.app.exceptions.UnexpectedErrorException;
import com.web.ecomm.app.pojo.Products;
import com.web.ecomm.app.service.IProductService;
import com.web.ecomm.app.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

@RestController
@CrossOrigin
@RequestMapping("/product")
public class ProductController {
    @Autowired
    IProductService productService;

    public ProductController() {
        System.out.println("in " + getClass().getName());
    }

    @GetMapping("/list") /*--------------------------------------------- Admin get All Product List Done-------------------------------------------------*/
    public APIResponseEntity getAllProductList() {
        System.out.println("in  get all Product list");
            return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, productService.getAllProducts());
    }

    @GetMapping("/galleryList") /*--------------------------------------------- User getGalleryProductList Done-------------------------------------------------*/
    public APIResponseEntity getGalleryProductList() {
        System.out.println("in  get gallery Product list");
            return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE,  productService.getGalleryProducts());
    }


    @GetMapping("/details/{prod_id}")/*--------------------------------------------- Admin/User getProduct Done-------------------------------------------------*/
    public APIResponseEntity getProduct(@PathVariable String prod_id) {
        System.out.println("in  get Product details");
        Products product = productService.getProductDetails(Integer.parseInt(prod_id));
        if (product != null) {
            return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, product);
        }
        throw new ResourceNotFoundException("Product not found for given product id", Constants.ERR_RESOURCE_NOT_FOUND);
    }

    @PostMapping("/add")/*--------------------------------------------- Admin addNewProduct Done-------------------------------------------------*/
    public APIResponseEntity addNewProduct(@RequestBody Products product) {
        System.out.println("in  add new Product : " + product);
        Products prod = productService.addProducts(product);
        if (prod != null) {
            return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, "Product added successfully");
        }
        throw new UnexpectedErrorException("Error while adding new  product", Constants.ERR_DEFAULT);
    }

    @PutMapping("/update/{prod_id}")/*--------------------------------------------- Admin updateProduct Done-------------------------------------------------*/
    public APIResponseEntity updateProduct(@RequestBody Products product, @PathVariable String prod_id) {
        System.out.println("in  update Product : ");
        Products prod = productService.updateProducts(Integer.parseInt(prod_id), product);
        if (prod != null) {
            return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, "Product Updated successfully");
        }
        throw new UnexpectedErrorException("Error while Updating  product", Constants.ERR_DEFAULT);
    }

    @DeleteMapping("/delete/{prod_id}")/*--------------------------------------------- Admin deleteProduct Done-------------------------------------------------*/
    public APIResponseEntity deleteProduct(@PathVariable String prod_id) throws IOException {
        System.out.println("in  Delete Product");
        return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, productService.deleteProducts(Integer.parseInt(prod_id)));
    }

    @PutMapping("/isActiveStatus/{prodId}/{status}") /*--------------------------------------------- Admin changeProductActiveStatus Done-------------------------------------------------*/
    public APIResponseEntity changeProductActiveStatus(@PathVariable String prodId, @PathVariable String status) {
        System.out.println("in change product active status");
        return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, productService.changeProductActiveStatus(Integer.parseInt(prodId), Integer.parseInt(status)));
    }

    @GetMapping("/getImage/{photo}")/*--------------------------------------------- User getProductImage Done-------------------------------------------------*/
    public ResponseEntity<?> getProductImage(@PathVariable String photo) throws IOException {
        System.out.println("in get product image");
        return new ResponseEntity(productService.getPhotoByName(photo), HttpStatus.OK);
    }

    @PutMapping("/uploadImage/{prodId}")/*--------------------------------------------- Admin uploadProductImage Done-------------------------------------------------*/
    public APIResponseEntity uploadProductImage(@PathVariable String prodId, @RequestParam("productImage") MultipartFile multipartFile) throws IOException {
        System.out.println("in upload product image");
        return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, productService.uploadProductImage(Integer.parseInt(prodId), multipartFile));
    }

    @DeleteMapping("/imageDelete/{prodId}")/*--------------------------------------------- Admin deleteProductImage Done-------------------------------------------------*/
    public APIResponseEntity deleteProductImage(@PathVariable String prodId) throws IOException {
        System.out.println("in delete product image");
        return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, productService.deleteProductImage(Integer.parseInt(prodId)));
    }

}
