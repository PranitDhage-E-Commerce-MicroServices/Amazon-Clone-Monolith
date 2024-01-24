package com.web.ecomm.app.service;

import com.web.ecomm.app.dao.ProductRepository;
import com.web.ecomm.app.exceptions.ResourceNotFoundException;
import com.web.ecomm.app.pojo.Products;
import com.web.ecomm.app.utils.Constants;
import com.web.ecomm.app.utils.ImageUploadUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements IProductService {

    @Autowired
    ProductRepository productRepo;

    @Override
    public List<Products> getAllProducts() {
        return productRepo.findAllProducts();
    }

    @Override
    public List<Products> getGalleryProducts() {
        return productRepo.findAllProducts().stream().filter(product -> product.getIsActive() != 0 && product.getProdQty() > 0).collect(Collectors.toList());
    }


    @Override
    public Products addProducts(Products product) {
        return productRepo.save(product);
    }

    @Override
    public Products updateProducts(int prodId, Products newProduct) {
            Products oldProduct = productRepo.findById(prodId).orElseThrow(() -> new ResourceNotFoundException("Product  not found for given Product Id : " + prodId, Constants.ERR_RESOURCE_NOT_FOUND));
            if (newProduct.getProdTitle() != null) oldProduct.setProdTitle(newProduct.getProdTitle());
            if (newProduct.getProdDescription() != null) oldProduct.setProdDescription(newProduct.getProdDescription());
            if (newProduct.getProdPrice() != null) oldProduct.setProdPrice(newProduct.getProdPrice());
            if (newProduct.getProdQty() != null) oldProduct.setProdQty(newProduct.getProdQty());
            if (newProduct.getPhoto() != null) oldProduct.setPhoto(newProduct.getPhoto());
            if (newProduct.getCategory() != null) oldProduct.setCategory(newProduct.getCategory());
            if (newProduct.getCompany() != null) oldProduct.setCompany(newProduct.getCompany());
            return productRepo.save(oldProduct);
    }

    @Override
    public String deleteProducts(int prodId) throws IOException {
        if (productRepo.existsById(prodId)) {
           deleteProductImage(prodId);
            productRepo.deleteById(prodId);
            return "Product Deleted Successfully";
        }
        throw new ResourceNotFoundException("Product  not found for given Product Id : " + prodId, Constants.ERR_RESOURCE_NOT_FOUND);
    }

    @Override
    public Products getProductDetails(int prodId) {
        return productRepo.findById(prodId).orElseThrow(() -> new ResourceNotFoundException("Product  not found for given Product Id : " + prodId, Constants.ERR_RESOURCE_NOT_FOUND));
    }

    @Override
    public String changeProductActiveStatus(int prodId, int status) {
        Products product = productRepo.findById(prodId).orElseThrow(() -> new ResourceNotFoundException("Product  not found for given Product Id : " + prodId, Constants.ERR_RESOURCE_NOT_FOUND));
        product.setIsActive(status);
        productRepo.save(product);
        return "Product Active status changed";
    }

    @Override
    public Integer countAllProduct() {
        return productRepo.countAllProduct();
    }

    @Override
    public byte[] getPhotoByName(String photo) throws IOException {
        String location = "src/main/resources/product-photos/";
        Path path = Paths.get(location, photo);
        return Files.readAllBytes(path);
    }


    @Override
    public String uploadProductImage(int prodId, MultipartFile file) throws IOException {
        Products product = productRepo.findById(prodId).orElseThrow(() -> new ResourceNotFoundException("Product  not found for given Product Id : " + prodId, Constants.ERR_RESOURCE_NOT_FOUND));
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String photo = ImageUploadUtils.generateImageName();    //Generate Random Photo name
        product.setPhoto(photo);
        productRepo.save(product);
        String uploadDir = "src/main/resources/product-photos/";
        ImageUploadUtils.saveFile(uploadDir, photo, file);
        return "Image Uploaded Successfully";
    }

    @Override
    public String deleteProductImage(int prodId) throws IOException {
        Products product = productRepo.findById(prodId).orElseThrow(() -> new ResourceNotFoundException("Product not found for given product id", Constants.ERR_RESOURCE_NOT_FOUND));
        if (product.getPhoto() != null) {
            String uploadDir = "src/main/resources/product-photos/";
            ImageUploadUtils.deleteFile(uploadDir, product.getPhoto());
            product.setPhoto(null);
            return "Product image deleted successfully";
        }
        return "Product image is not available";
    }

}
