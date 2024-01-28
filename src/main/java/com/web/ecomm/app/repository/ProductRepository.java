package com.web.ecomm.app.repository;

import com.web.ecomm.app.pojo.Products;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@PropertySources({
        @PropertySource("classpath:sql.properties")
})
public interface ProductRepository extends JpaRepository<Products, Integer> {

    @Value("${PRODUCT.FIND_ALL_PRODUCTS:}")
    String FIND_ALL_PRODUCTS = "SELECT p FROM Products p JOIN FETCH p.category ct JOIN FETCH p.company cp";

    @Value("${PRODUCT.COUNT_ALL_PRODUCTS:}")
    String COUNT_ALL_PRODUCTS = "SELECT COUNT(p) FROM Products p";

    @Query(value = FIND_ALL_PRODUCTS)
    List<Products> findAllProducts();

    @Query(value = COUNT_ALL_PRODUCTS)
    Integer countAllProduct();

}
