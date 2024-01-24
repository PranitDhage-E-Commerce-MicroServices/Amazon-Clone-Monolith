package com.web.ecomm.app.controller;

import com.web.ecomm.app.dto.APIResponseEntity;
import com.web.ecomm.app.exceptions.ResourceNotFoundException;
import com.web.ecomm.app.exceptions.UnexpectedErrorException;
import com.web.ecomm.app.pojo.Category;
import com.web.ecomm.app.service.ICategoryService;
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
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    ICategoryService categoryService;

    public CategoryController() {
        System.out.println("in " + getClass().getName());
    }

    @GetMapping("/list")/*------------------------------------------Admin getAllCategoryList Done--------------------------------------------------------*/
    public APIResponseEntity getAllCategoryList() {
        System.out.println("in  get all category list");
            return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, categoryService.getAllCategories());
    }

    @GetMapping("/details/{catId}")/*------------------------------------------Admin getCategoryDetailsById Done--------------------------------------------------------*/
    public APIResponseEntity getCategoryDetailsById(@PathVariable String catId) {
        System.out.println("in category details");
        Category category = categoryService.getCategoryDetailsById(Integer.parseInt(catId));
        if (category != null) {
            return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, category);
        }
        throw new ResourceNotFoundException("Category not found for given category id" + catId, Constants.ERR_RESOURCE_NOT_FOUND);
    }


    @PostMapping("/add")/*------------------------------------------Admin addNewCategory Done--------------------------------------------------------*/
    public APIResponseEntity addNewCategory(@RequestBody Category category) {
        System.out.println("in  add new category");
        Category cat = categoryService.addCategory(category);
        if (cat != null) {
            return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, "Category added successfully");
        }
        throw new UnexpectedErrorException("Error while adding new  category", Constants.ERR_DEFAULT);
    }

    @PutMapping("/update/{cat_id}")/*------------------------------------------Admin updateCategory Done--------------------------------------------------------*/
    public APIResponseEntity updateCategory(@RequestBody Category category, @PathVariable String cat_id) {
        System.out.println("in  update category");
        Category cat = categoryService.updateCategory(Integer.parseInt(cat_id), category);
        if (cat != null) {
            return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, "Category Updated successfully");
        }
        throw new UnexpectedErrorException("Error while updating  category", Constants.ERR_DEFAULT);
    }

    @DeleteMapping("/delete/{cat_id}")/*------------------------------------------Admin deleteCategory Done--------------------------------------------------------*/
    public APIResponseEntity deleteCategory(@PathVariable String cat_id) {
        System.out.println("in  Delete category");
        return new APIResponseEntity(Constants.STATUS_SUCCESS, Constants.SUCCESS_CODE, categoryService.deleteCategory(Integer.parseInt(cat_id)));
    }

}
