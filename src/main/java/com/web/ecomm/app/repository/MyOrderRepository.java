package com.web.ecomm.app.repository;

import com.web.ecomm.app.pojo.Myorder;
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
public interface MyOrderRepository extends JpaRepository<Myorder, Integer> {

    @Value("${MY_ORDER.FIND_ALL_USER_ORDERS:}")
    String FIND_ALL_USER_ORDERS = "";

    @Value("${MY_ORDER.FIND_ALL_BY_USER_USER_ID:}")
    String FIND_ALL_BY_USER_USER_ID = "";

    @Value("${MY_ORDER.COUNT_ALL_USER_ORDERS:}")
    String COUNT_ALL_USER_ORDERS = "";

    @Value("${MY_ORDER.COUNT_ALL_ACTIVE_USER_ORDERS:}")
    String COUNT_ALL_ACTIVE_USER_ORDERS = "";

    @Query(value = FIND_ALL_USER_ORDERS, nativeQuery = true)
    List<Myorder> findAllUserOrders();

    @Query(value = FIND_ALL_BY_USER_USER_ID, nativeQuery = true)
    List<Myorder> findAllByUserUserId(@Param("userId") int userId);

    @Query(value = COUNT_ALL_USER_ORDERS, nativeQuery = true)
    Integer countAllUserOrders();

    @Query(value = COUNT_ALL_ACTIVE_USER_ORDERS, nativeQuery = true)
    Integer countAllActiveUserOrders();

}
