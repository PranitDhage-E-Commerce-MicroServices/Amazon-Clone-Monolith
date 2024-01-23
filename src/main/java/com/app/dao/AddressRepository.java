package com.app.dao;

import com.app.exceptions.SystemException;
import com.app.pojo.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    @Query(value = "SELECT a FROM Address a LEFT OUTER JOIN FETCH a.user WHERE a.user.userId = :userId")
    List<Address> findAllByUserUserId(@Param("userId") int userId) throws SystemException;
}
