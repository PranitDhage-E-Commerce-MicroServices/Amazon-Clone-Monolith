package com.web.ecomm.app.repository;

import com.web.ecomm.app.pojo.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepositary extends JpaRepository<Company, Integer> {
}