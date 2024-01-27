package com.web.ecomm.app.controller;

import com.web.ecomm.app.models.response.APIResponseEntity;
import com.web.ecomm.app.exceptions.ResourceNotFoundException;
import com.web.ecomm.app.exceptions.UnexpectedErrorException;
import com.web.ecomm.app.pojo.Company;
import com.web.ecomm.app.service.ICompanyService;
import com.web.ecomm.app.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    ICompanyService companyService;

    public CompanyController() {
        System.out.println("in " + getClass().getName());
    }

    @GetMapping("/list")  /*----------------------------------------------------- Admin get All Company List Done*--------------------------------------------------*/
    public APIResponseEntity getAllCompanyList() {
        System.out.println("in  get all company list");
            return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, companyService.getAllCompanies());
    }

    @GetMapping("/details/{compId}") /*---------------------------------- Admin get Company Details Done*--------------------------------------*/
    public APIResponseEntity getCompanyDetailsById(@PathVariable String compId) {
        System.out.println("in  company details");
        Company company = companyService.getCompanyDetailsById(Integer.parseInt(compId));
        if (company != null) {
            return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, company);
        }
        throw new ResourceNotFoundException("Company list not found", Constants.ERR_RESOURCE_NOT_FOUND);
    }

    @PostMapping("/add")/*---------------------------------- Admin add New Company Done*--------------------------------------*/
    public APIResponseEntity addNewCompany(@RequestBody Company company) {
        System.out.println("in  add new company");
        Company comp = companyService.addCompany(company);
        if (comp != null) {
            return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, "Company added successfully");
        }
        throw new UnexpectedErrorException("Error while adding new  company", Constants.ERR_DEFAULT);
    }

    @PutMapping("/update/{compId}")/*---------------------------------- Admin update Company Done*--------------------------------------*/
    public APIResponseEntity updateCompany(@RequestBody Company company, @PathVariable String compId) {
        System.out.println("in  update company");
        Company comp = companyService.updateCompany(Integer.parseInt(compId), company);
        if (comp != null) {
            return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, "Company updated successfully");
        }
        throw new UnexpectedErrorException("Error while updating company", Constants.ERR_DEFAULT);
    }

    @DeleteMapping("/delete/{compId}")
    public APIResponseEntity deleteCompany(@PathVariable String compId) {
        System.out.println("in  Delete company");
        return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, companyService.deleteCompany(Integer.parseInt(compId)));
    }

}
