package com.web.ecomm.app.service;

import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.exceptions.ValidationException;
import com.web.ecomm.app.pojo.Products;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IProductService {

    /**
     * Get all the Product List for Admin
     *
     * @return List of Products for Admin
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.SystemException   SystemException
     */
    List<Products> getAllProducts() throws BusinessException, ValidationException, SystemException;

    /**
     * Get all the Gallery Product List
     *
     * @return List of Gallery Products
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.SystemException   SystemException
     */
    List<Products> getGalleryProducts()  throws BusinessException, ValidationException, SystemException;

    /**
     * Get all the Product Details for given Product Id
     *
     * @return Product Details for Given Product Id
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.SystemException   SystemException
     */
    Products getProductDetails(int prodId)  throws BusinessException, ValidationException, SystemException;

    /**
     * Saves new Product for Admin
     *
     * @param product Product Request Body
     * @return Saved Product
     * @throws com.web.ecomm.app.exceptions.BusinessException   BusinessException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     * @throws com.web.ecomm.app.exceptions.SystemException     SystemException
     */
    Products addProducts(Products product) throws BusinessException, ValidationException, SystemException;

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
    Products updateProducts(int prodId, Products product) throws BusinessException, ValidationException, SystemException;

    /**
     * Deletes Product for given Product Id
     *
     * @param prodId Product Identifier
     * @return Deleted Product Status
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.SystemException   SystemException
     */
    boolean deleteProducts(int prodId) throws BusinessException, ValidationException, SystemException, IOException;

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
    boolean changeProductActiveStatus(int prodId, int status) throws BusinessException, ValidationException, SystemException;

    /**
     * Get Product Image for given Photo Name
     *
     * @param photo Photo IDENTIFIER
     * @return Image for given Photo Id
     * @throws BusinessException BusinessException
     * @throws SystemException   SystemException
     */
    byte[] getPhotoByName(String photo)  throws BusinessException, ValidationException, SystemException, IOException;


    /**
     * Upload Product Image for Given Product Id for Admin
     *
     * @param file Product Image Status
     * @param prodId        Product Identifier
     * @return Updated Product Status
     * @throws com.web.ecomm.app.exceptions.BusinessException   BusinessException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     * @throws com.web.ecomm.app.exceptions.SystemException     SystemException
     */
    boolean uploadProductImage(int prodId, MultipartFile file) throws BusinessException, ValidationException, SystemException, IOException;

    /**
     * Deletes Product Image for given Product Id
     *
     * @param prodId Product Identifier
     * @return Deleted Product Image Status
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.SystemException   SystemException
     */
    boolean deleteProductImage(int prodId) throws BusinessException, ValidationException, SystemException, IOException;

    /**
     * Get all dashboard count for admin
     *
     * @return DashboardCountResponse Dashboard Count Response
     * @throws BusinessException BusinessException
     * @throws SystemException   SystemException
     */
    Integer countAllProduct()  throws BusinessException, ValidationException, SystemException;

}
