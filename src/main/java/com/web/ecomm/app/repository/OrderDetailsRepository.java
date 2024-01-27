package com.web.ecomm.app.repository;

import com.web.ecomm.app.pojo.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {

    @Query(value = "SELECT o FROM OrderDetails o " +
            "JOIN FETCH o.myorder m LEFT OUTER JOIN FETCH m.user JOIN FETCH m.address " +
            "JOIN FETCH o.product p JOIN FETCH p.category ct JOIN FETCH p.company cp " +
            "WHERE o.myorder.myorderId = :myorderId")
    List<OrderDetails> findAllByMyorderMyorderId(@Param("myorderId") int myorderId);
}
