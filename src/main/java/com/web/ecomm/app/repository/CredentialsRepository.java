package com.web.ecomm.app.repository;

import com.web.ecomm.app.pojo.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialsRepository extends JpaRepository<Credentials, Integer> {
    Credentials findByEmail(String email);
}
