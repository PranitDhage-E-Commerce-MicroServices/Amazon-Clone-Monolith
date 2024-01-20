package com.app.service;

import com.app.pojo.Company;

import java.util.List;

public interface ICompanyService {
    List<Company> getAllCompanies();

    Company addCompany(Company company);

    Company updateCompany(int compId, Company company);

    String deleteCompany(int compId);

    Integer countAllCompany();

    Company getCompanyDetailsById(int compId);
}
