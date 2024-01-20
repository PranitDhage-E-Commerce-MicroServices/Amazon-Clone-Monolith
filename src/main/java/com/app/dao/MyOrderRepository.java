package com.app.dao;

import com.app.pojo.Myorder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyOrderRepository extends JpaRepository<Myorder, Integer> {

    @Query(value = "SELECT m FROM Myorder m LEFT OUTER JOIN FETCH m.user JOIN FETCH m.address")
    List<Myorder> findAllUserOrders();

    @Query(value = "SELECT m FROM Myorder m LEFT OUTER JOIN FETCH m.user JOIN FETCH m.address WHERE m.user.userId = :userId")
    List<Myorder> findAllByUserUserId(@Param("userId") int userId);

    @Query(value = "SELECT  COUNT(m) FROM Myorder m")
    Integer countAllUserOrders();

    @Query(value = "SELECT  COUNT(m) FROM Myorder m WHERE m.deliveryStatus NOT IN ('Cancelled', 'Delivered')")
    Integer countAllActiveUserOrders();


}
