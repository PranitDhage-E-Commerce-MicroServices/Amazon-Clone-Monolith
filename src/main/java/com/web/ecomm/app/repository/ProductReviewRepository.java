package com.web.ecomm.app.repository;

import com.web.ecomm.app.pojo.ProductReview;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@PropertySources({
        @PropertySource("classpath:sql.properties")
})
public interface ProductReviewRepository extends JpaRepository<ProductReview, Integer> {

    @Value("${REVIEW.FIND_ALL_BY_PRODUCT_ID:}")
    String FIND_ALL_BY_PRODUCT_ID = "SELECT r FROM ProductReview r " +
            "LEFT OUTER JOIN FETCH r.user " +
            "JOIN FETCH r.product p JOIN FETCH p.category ct JOIN FETCH p.company cp " +
            "WHERE r.product.prodId = :prodId";

    @Value("${REVIEW.FIND_AVERAGE_OF_PRODUCT_REVIEWS:}")
    String FIND_AVERAGE_OF_PRODUCT_REVIEWS = "SELECT AVG(p.rating) FROM ProductReview p WHERE p.product.prodId = :prodId";


    @Query(value = FIND_ALL_BY_PRODUCT_ID)
    List<ProductReview> findAllByProductProdId(@Param("prodId") int prodId);

    @Query(value = FIND_AVERAGE_OF_PRODUCT_REVIEWS)
    double getAverageOfProductReview(@Param("prodId") int prodId);
}
