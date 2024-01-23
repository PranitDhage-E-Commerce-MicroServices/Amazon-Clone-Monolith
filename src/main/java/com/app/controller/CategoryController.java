package com.app.controller;

import com.app.dto.ResponseDTO;
import com.app.exceptions.ResourceNotFoundException;
import com.app.exceptions.UnexpectedErrorException;
import com.app.pojo.Category;
import com.app.service.ICategoryService;
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
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    ICategoryService categoryService;

    public CategoryController() {
        System.out.println("in " + getClass().getName());
    }

    @GetMapping("/list")/*------------------------------------------Admin getAllCategoryList Done--------------------------------------------------------*/
    public ResponseDTO getAllCategoryList() {
        System.out.println("in  get all category list");
            return new ResponseDTO(true, categoryService.getAllCategories());
    }

    @GetMapping("/details/{catId}")/*------------------------------------------Admin getCategoryDetailsById Done--------------------------------------------------------*/
    public ResponseDTO getCategoryDetailsById(@PathVariable String catId) {
        System.out.println("in category details");
        Category category = categoryService.getCategoryDetailsById(Integer.parseInt(catId));
        if (category != null) {
            return new ResponseDTO(true, category);
        }
        throw new ResourceNotFoundException("Category not found for given category id" + catId, Constants.ERR_RESOURCE_NOT_FOUND);
    }


    @PostMapping("/add")/*------------------------------------------Admin addNewCategory Done--------------------------------------------------------*/
    public ResponseDTO addNewCategory(@RequestBody Category category) {
        System.out.println("in  add new category");
        Category cat = categoryService.addCategory(category);
        if (cat != null) {
            return new ResponseDTO(true, "Category added successfully");
        }
        throw new UnexpectedErrorException("Error while adding new  category", Constants.ERR_DEFAULT);
    }

    @PutMapping("/update/{cat_id}")/*------------------------------------------Admin updateCategory Done--------------------------------------------------------*/
    public ResponseDTO updateCategory(@RequestBody Category category, @PathVariable String cat_id) {
        System.out.println("in  update category");
        Category cat = categoryService.updateCategory(Integer.parseInt(cat_id), category);
        if (cat != null) {
            return new ResponseDTO(true, "Category Updated successfully");
        }
        throw new UnexpectedErrorException("Error while updating  category", Constants.ERR_DEFAULT);
    }

    @DeleteMapping("/delete/{cat_id}")/*------------------------------------------Admin deleteCategory Done--------------------------------------------------------*/
    public ResponseDTO deleteCategory(@PathVariable String cat_id) {
        System.out.println("in  Delete category");
        return new ResponseDTO(true, categoryService.deleteCategory(Integer.parseInt(cat_id)));
    }

}
