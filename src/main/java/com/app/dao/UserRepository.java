package com.app.dao;

import com.app.pojo.Role;
import com.app.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserEmailAndUserPassword(String email, String password);

    User findByUserEmail(String user_email);

    List<User> findAllByUserRole(Role role);

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
