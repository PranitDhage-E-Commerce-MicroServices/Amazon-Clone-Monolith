package com.web.ecomm.app.repository;

import com.web.ecomm.app.pojo.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
@Query(value = "SELECT c FROM Cart c LEFT OUTER JOIN FETCH c.user u JOIN FETCH c.product p JOIN FETCH p.category JOIN FETCH p.company WHERE c.user.userId = :userId")
    List<Cart> findAllByUserUserId(@Param("userId") int userId);

    void deleteAllByUserUserId( int userId);
}
