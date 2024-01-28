package com.web.ecomm.app.service;

import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.exceptions.ValidationException;
import com.web.ecomm.app.pojo.Company;

import java.util.List;

public interface ICompanyService {

    /**
     * Get all the Company List for Admin
     *
     * @return List of Company for Admin
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.SystemException   SystemException
     */
    List<Company> getAllCompanies() throws BusinessException, ValidationException, SystemException;

    /**
     * Get the Company Details By Company Id for Admin
     *
     * @param compId Company Identifier
     * @return Company Details for Admin
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.SystemException   SystemException
     */
    Company getCompanyDetailsById(int compId) throws BusinessException, ValidationException, SystemException;

    /**
     * Saves new Company for Admin
     *
     * @param company Company Request Body
     * @return Saved Company
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     * @throws com.web.ecomm.app.exceptions.SystemException SystemException
     */
    Company addCompany(Company company) throws BusinessException, ValidationException, SystemException;

    /**
     * Updates Company for given Company Id By Admin
     *
     * @param compId   Company Identifier
     * @param company Company Request Body
     * @return Updated Company
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     * @throws com.web.ecomm.app.exceptions.SystemException SystemException
     */
    Company updateCompany(int compId, Company company) throws BusinessException, ValidationException, SystemException;

    /**
     * Deletes company for given company Id
     *
     * @param compId Company Identifier
     * @return Deleted Company Status
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.SystemException SystemException
     */
    boolean deleteCompany(int compId) throws BusinessException, ValidationException, SystemException;

    Integer countAllCompany() throws BusinessException, ValidationException, SystemException;
}
