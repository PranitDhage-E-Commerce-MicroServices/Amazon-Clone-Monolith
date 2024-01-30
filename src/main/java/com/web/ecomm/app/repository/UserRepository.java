package com.web.ecomm.app.repository;

import com.web.ecomm.app.pojo.Role;
import com.web.ecomm.app.pojo.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@PropertySources({
        @PropertySource("classpath:sql.properties")
})
public interface UserRepository extends JpaRepository<User, Integer> {

    @Value("${USER.GET_ALL_COUNT:}")
    String GET_ALL_COUNT = "SELECT " +
            "(SELECT COUNT(*) FROM user) as userCount, " +
            "(SELECT COUNT(*) FROM product) as productCount, " +
            "(SELECT COUNT(*) FROM myorder) as MyOrderCount, " +
            "(SELECT COUNT(*) FROM myorder WHERE delivery_status NOT IN ('Cancelled', 'Delivered')) as ActiveOrderCount, " +
            "(SELECT COUNT(*) FROM company) as companyCount, " +
            "(SELECT COUNT(*) FROM category) as categoryCount " +
            "FROM DUAL";

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);

    List<User> findAllByRole(Role role);

    @Query(value = GET_ALL_COUNT)
    Object getAllCount();

}
