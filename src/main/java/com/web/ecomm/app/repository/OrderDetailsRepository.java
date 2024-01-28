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
    String FIND_ALL_BY_MY_ORDER_ID = "SELECT o FROM OrderDetails o JOIN FETCH o.myorder m " +
            "  LEFT OUTER JOIN FETCH m.user JOIN FETCH m.address JOIN FETCH o.product p " +
            "  JOIN FETCH p.category ct JOIN FETCH p.company cp WHERE o.myorder.myorderId = :myOrderId";

    @Query(value = FIND_ALL_BY_MY_ORDER_ID)
    List<OrderDetails> findAllByMyorderMyorderId(@Param("myOrderId") int myOrderId);
}
