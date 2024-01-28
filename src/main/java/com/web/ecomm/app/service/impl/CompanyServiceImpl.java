package com.web.ecomm.app.service.impl;

import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.ResourceNotFoundException;
import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.exceptions.ValidationException;
import com.web.ecomm.app.pojo.Company;
import com.web.ecomm.app.repository.CompanyRepository;
import com.web.ecomm.app.service.ICompanyService;
import com.web.ecomm.app.utils.Constants;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
public class CompanyServiceImpl implements ICompanyService {

    private final CompanyRepository companyRepo;

    @Autowired
    public CompanyServiceImpl(final CompanyRepository companyRepo) {
        this.companyRepo = companyRepo;
    }

    @Override
    public List<Company> getAllCompanies()
            throws BusinessException, ValidationException, SystemException {

        try {
            return companyRepo.findAll();
        } catch (Exception e) {
            log.error("Exception While Getting All Company for Admin - Message: {}", e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public Company getCompanyDetailsById(int compId)
            throws BusinessException, ValidationException, SystemException {

        try {
            return companyRepo.findById(compId)
                    .orElseThrow(
                            () -> new ResourceNotFoundException(
                                    "Company not found for given comp Id : " + compId,
                                    Constants.ERR_RESOURCE_NOT_FOUND
                            )
                    );
        } catch (ResourceNotFoundException e) {
            log.error("Exception While Saving Company: {}", e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public Company addCompany(Company company)
            throws BusinessException, ValidationException, SystemException {

        try {
            return companyRepo.save(company);
        } catch (Exception e) {
            log.error("Exception While Saving Company: {}", e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public Company updateCompany(int compId, Company newCompany)
            throws BusinessException, ValidationException, SystemException {

        try {
            Company oldCompany = companyRepo.findById(compId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Company not found for given Company Id : " + compId,
                                        Constants.ERR_RESOURCE_NOT_FOUND)
                        );

            if (StringUtils.isNotBlank(newCompany.getCompTitle()))
                oldCompany.setCompTitle(newCompany.getCompTitle());

            if (StringUtils.isNotBlank(newCompany.getCompDescription()))
                oldCompany.setCompDescription(newCompany.getCompDescription());

            return companyRepo.save(oldCompany);
        } catch (ResourceNotFoundException e) {
            log.error("Exception While Updating Company: {}", e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public boolean deleteCompany(int compId)
            throws BusinessException, ValidationException, SystemException {

        try {
            if (!companyRepo.existsById(compId)) {
                throw new ResourceNotFoundException(
                        "Company not found for given Company Id : " + compId,
                        Constants.ERR_RESOURCE_NOT_FOUND);
            }

            companyRepo.deleteById(compId);
            return true;
        } catch (ResourceNotFoundException e) {
            log.error("Exception While deleting Company for Id: {} - {}", compId, e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public Integer countAllCompany()
            throws BusinessException, ValidationException, SystemException {

        try {
            return companyRepo.findAll().size();
        } catch (Exception e) {
            log.error("Exception While getting count of all company {}", e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

}
