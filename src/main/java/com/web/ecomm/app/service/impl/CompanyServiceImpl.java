package com.web.ecomm.app.service.impl;

import com.web.ecomm.app.repository.CompanyRepositary;
import com.web.ecomm.app.exceptions.ResourceNotFoundException;
import com.web.ecomm.app.pojo.Company;
import com.web.ecomm.app.service.ICompanyService;
import com.web.ecomm.app.utils.Constants;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CompanyServiceImpl implements ICompanyService {

    @Autowired
    CompanyRepositary companyRepo;

    @Override
    public List<Company> getAllCompanies() {
        return companyRepo.findAll();
    }

    @Override
    public Company addCompany(Company company) {
        return companyRepo.save(company);
    }

    @Override
    public Company updateCompany(int compId, Company newCompany) {
        if (companyRepo.existsById(compId)) {
            Company oldCompany = companyRepo.findById(compId).get();
            if (newCompany.getCompTitle() != "") oldCompany.setCompTitle(newCompany.getCompTitle());
            if (newCompany.getCompDescription() != "") oldCompany.setCompDescription(newCompany.getCompDescription());
            return companyRepo.save(oldCompany);
        }
        throw new ResourceNotFoundException("Company not found for given comp Id : " + compId, Constants.ERR_RESOURCE_NOT_FOUND);
    }

    @Override
    public String deleteCompany(int compId) {
        if (companyRepo.existsById(compId)) {
            companyRepo.deleteById(compId);
            return "Company Deleted Successfully ";
        }
        throw new ResourceNotFoundException("Company not found for given comp Id : " + compId, Constants.ERR_RESOURCE_NOT_FOUND);
    }

    @Override
    public Integer countAllCompany() {
        return companyRepo.findAll().size();
    }

    @Override
    public Company getCompanyDetailsById(int compId) {
        return companyRepo.findById(compId).orElseThrow(() -> new ResourceNotFoundException("Company not found for given comp Id : " + compId, Constants.ERR_RESOURCE_NOT_FOUND));
    }
}
