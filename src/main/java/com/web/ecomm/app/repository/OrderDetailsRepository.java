package com.web.ecomm.app.repository;

import com.web.ecomm.app.pojo.OrderDetails;
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
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {

    @Value("${ORDER_DETAIL.FIND_ALL_BY_MY_ORDER_ID:}")
    String FIND_ALL_BY_MY_ORDER_ID = "";

    @Query(value = FIND_ALL_BY_MY_ORDER_ID, nativeQuery = true)
    List<OrderDetails> findAllByMyorderMyorderId(@Param("myOrderId") int myOrderId);
}
