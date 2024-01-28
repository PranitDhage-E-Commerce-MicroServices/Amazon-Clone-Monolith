package com.web.ecomm.app.service.impl;

import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.ResourceNotFoundException;
import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.exceptions.ValidationException;
import com.web.ecomm.app.pojo.Products;
import com.web.ecomm.app.repository.ProductRepository;
import com.web.ecomm.app.service.IProductService;
import com.web.ecomm.app.utils.Constants;
import com.web.ecomm.app.utils.ImageUploadUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepo;

    @Autowired
    public ProductServiceImpl(final ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public List<Products> getAllProducts()
            throws BusinessException, ValidationException, SystemException {
        try {
            return productRepo.findAllProducts();
        } catch (Exception e) {
            log.error("Exception While Getting All Products - Message: {}", e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public List<Products> getGalleryProducts()
            throws BusinessException, ValidationException, SystemException {

        try {
            return productRepo.findAllProducts()
                    .stream()
                    .filter(
                            product -> product.getIsActive() != 0 &&
                                    product.getProdQty() > 0
                    ).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Exception While Getting All Gallery Product - Message: {}", e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }


    @Override
    public Products addProducts(Products product)
            throws BusinessException, ValidationException, SystemException {

        try {
            return productRepo.save(product);
        } catch (Exception e) {
            log.error("Exception While Saving Products: {}", e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public Products updateProducts(int prodId, Products newProduct)
            throws BusinessException, ValidationException, SystemException {

        try {
            Products oldProduct = productRepo.findById(prodId)
                    .orElseThrow(
                            () -> new ResourceNotFoundException(
                                    "Product  not found for given Product Id : " + prodId,
                                    Constants.ERR_RESOURCE_NOT_FOUND
                            )
                    );

            if (StringUtils.isNotBlank(newProduct.getProdTitle()))
                oldProduct.setProdTitle(newProduct.getProdTitle());

            if (StringUtils.isNotBlank(newProduct.getProdDescription()))
                oldProduct.setProdDescription(newProduct.getProdDescription());

            if (newProduct.getProdPrice() != null)
                oldProduct.setProdPrice(newProduct.getProdPrice());

            if (newProduct.getProdQty() != null)
                oldProduct.setProdQty(newProduct.getProdQty());

            if (StringUtils.isNotBlank(newProduct.getPhoto()))
                oldProduct.setPhoto(newProduct.getPhoto());

            if (newProduct.getCategory() != null)
                oldProduct.setCategory(newProduct.getCategory());

            if (newProduct.getCompany() != null)
                oldProduct.setCompany(newProduct.getCompany());

            return productRepo.save(oldProduct);
        } catch (ResourceNotFoundException e) {
            log.error("Exception While Updating Products: {}", e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public boolean deleteProducts(int prodId)
            throws BusinessException, ValidationException, SystemException, IOException {

        if (!productRepo.existsById(prodId)) {
            throw new ResourceNotFoundException(
                    "Product not found for given Product Id : " + prodId,
                    Constants.ERR_RESOURCE_NOT_FOUND);
        }

        try {
            deleteProductImage(prodId);
        } catch (Exception e) {
            log.error("Exception While deleting Product Image for Id: {} - {}", prodId, e.getMessage());
        }

        productRepo.deleteById(prodId);
        return true;
    }

    @Override
    public Products getProductDetails(int prodId)
            throws BusinessException, ValidationException, SystemException {

        try {
            return productRepo.findById(prodId)
                    .orElseThrow(
                            () -> new ResourceNotFoundException(
                                    "Product  not found for given Product Id : " + prodId,
                                    Constants.ERR_RESOURCE_NOT_FOUND
                            )
                    );
        } catch (ResourceNotFoundException e) {
            log.error("Exception While Getting Product for Given Product Id : {}, Message: {}", prodId, e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public boolean changeProductActiveStatus(int prodId, int status)
            throws BusinessException, ValidationException, SystemException {
        try {
            Products product = productRepo.findById(prodId)
                    .orElseThrow(
                            () -> new ResourceNotFoundException(
                                    "Product  not found for given Product Id : " + prodId,
                                    Constants.ERR_RESOURCE_NOT_FOUND
                            )
                    );

            product.setIsActive(status);
            productRepo.save(product);
            return true;

        } catch (ResourceNotFoundException e) {
            log.error("Exception While Changing Product Active Status for Product Id : {}, Message: {}", prodId, e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public Integer countAllProduct()
            throws BusinessException, ValidationException, SystemException {

        try {
            return productRepo.countAllProduct();
        } catch (Exception e) {
            log.error("Exception While Getting Count Of All Products - Message: {}", e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public byte[] getPhotoByName(String photo)
            throws BusinessException, ValidationException, SystemException, IOException {

        try {
            String location = "src/main/resources/product-photos/";
            Path path = Paths.get(location, photo);
            return Files.readAllBytes(path);
        } catch (Exception e) {
            log.error("Exception While Getting Photo By Name: {}, Message: {}", photo, e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public boolean uploadProductImage(int prodId, MultipartFile file)
            throws BusinessException, ValidationException, SystemException, IOException {

        try {
            Products product = productRepo.findById(prodId)
                    .orElseThrow(
                            () -> new ResourceNotFoundException(
                                    "Product  not found for given Product Id : " + prodId,
                                    Constants.ERR_RESOURCE_NOT_FOUND
                            )
                    );

//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String photo = ImageUploadUtils.generateImageName();    //Generate Random Photo name
            product.setPhoto(photo);
            productRepo.save(product);

            String uploadDir = "src/main/resources/product-photos/";
            ImageUploadUtils.saveFile(uploadDir, photo, file);
            return true;

        } catch (Exception e) {
            log.error("Exception While Uploading Photo for Product Id: {}, Message: {}", prodId, e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public boolean deleteProductImage(int prodId)
            throws BusinessException, ValidationException, SystemException, IOException {

        try {
            Products product = productRepo.findById(prodId)
                    .orElseThrow(
                            () -> new ResourceNotFoundException(
                                    "Product not found for given product id" + prodId,
                                    Constants.ERR_RESOURCE_NOT_FOUND
                            )
                    );

            if (product.getPhoto() != null) {
                String uploadDir = "src/main/resources/product-photos/";
                ImageUploadUtils.deleteFile(uploadDir, product.getPhoto());
                product.setPhoto(null);
                return true;
            }

            return false;

        } catch (Exception e) {
            log.error("Exception While Deleting Photo for Product Id: {}, Message: {}", prodId, e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

}
