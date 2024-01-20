package com.app.dao;

import com.app.pojo.ProductReview;
import com.app.pojo.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Integer> {

    @Query(value = "SELECT r FROM ProductReview r " +
            "LEFT OUTER JOIN FETCH r.user " +
            "JOIN FETCH r.product p JOIN FETCH p.category ct JOIN FETCH p.company cp " +
            " WHERE r.product.prodId = :prodId")
    List<ProductReview> findAllByProductProdId(@Param("prodId") int prodId);

    @Query("SELECT AVG(p.rating) FROM ProductReview p WHERE p.product.prodId = :prodId")
    double getAverageOfProductReview(@Param("prodId") int prodId);
}
