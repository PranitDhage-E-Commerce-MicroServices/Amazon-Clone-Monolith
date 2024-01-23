package com.app.controller;

import com.app.dto.ResponseDTO;
import com.app.exceptions.ResourceNotFoundException;
import com.app.exceptions.UnexpectedErrorException;
import com.app.pojo.Company;
import com.app.service.ICompanyService;
import com.app.utils.Constants;
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
    public ResponseDTO getAllCompanyList() {
        System.out.println("in  get all company list");
            return new ResponseDTO(true, companyService.getAllCompanies());
    }

    @GetMapping("/details/{compId}") /*---------------------------------- Admin get Company Details Done*--------------------------------------*/
    public ResponseDTO getCompanyDetailsById(@PathVariable String compId) {
        System.out.println("in  company details");
        Company company = companyService.getCompanyDetailsById(Integer.parseInt(compId));
        if (company != null) {
            return new ResponseDTO(true, company);
        }
        throw new ResourceNotFoundException("Company list not found", Constants.ERR_RESOURCE_NOT_FOUND);
    }

    @PostMapping("/add")/*---------------------------------- Admin add New Company Done*--------------------------------------*/
    public ResponseDTO addNewCompany(@RequestBody Company company) {
        System.out.println("in  add new company");
        Company comp = companyService.addCompany(company);
        if (comp != null) {
            return new ResponseDTO(true, "Company added successfully");
        }
        throw new UnexpectedErrorException("Error while adding new  company", Constants.ERR_DEFAULT);
    }

    @PutMapping("/update/{compId}")/*---------------------------------- Admin update Company Done*--------------------------------------*/
    public ResponseDTO updateCompany(@RequestBody Company company, @PathVariable String compId) {
        System.out.println("in  update company");
        Company comp = companyService.updateCompany(Integer.parseInt(compId), company);
        if (comp != null) {
            return new ResponseDTO(true, "Company updated successfully");
        }
        throw new UnexpectedErrorException("Error while updating company", Constants.ERR_DEFAULT);
    }

    @DeleteMapping("/delete/{compId}")
    public ResponseDTO deleteCompany(@PathVariable String compId) {
        System.out.println("in  Delete company");
        return new ResponseDTO(true, companyService.deleteCompany(Integer.parseInt(compId)));
    }

}
