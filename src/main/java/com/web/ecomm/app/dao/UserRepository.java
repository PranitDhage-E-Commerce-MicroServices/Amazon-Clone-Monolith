package com.web.ecomm.app.dao;

import com.web.ecomm.app.pojo.Role;
import com.web.ecomm.app.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);

    List<User> findAllByRole(Role role);

    @Query(value = "SELECT \n" +
            "(SELECT COUNT(*) FROM user) as userCount,\n" +
            "(SELECT COUNT(*) FROM product) as productCount,\n" +
            "(SELECT COUNT(*) FROM myorder) as MyOrderCount,\n" +
            "(SELECT COUNT(*) FROM myorder WHERE delivery_status NOT IN ('Cancelled', 'Delivered')) as ActiveOrderCount,\n" +
            "(SELECT COUNT(*) FROM company) as companyCount,\n" +
            "(SELECT COUNT(*) FROM category) as categoryCount\n" +
            "FROM DUAL;", nativeQuery = true)
    Object getAllCount();
}
