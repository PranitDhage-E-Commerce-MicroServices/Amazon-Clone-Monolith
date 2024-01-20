package com.app.controller;

import com.app.customExceptions.ResourceNotFoundException;
import com.app.customExceptions.UnexpectedErrorException;
import com.app.dto.ResponseDTO;
import com.app.pojo.Category;
import com.app.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        throw new ResourceNotFoundException("Category not found for given category id" + catId);
    }


    @PostMapping("/add")/*------------------------------------------Admin addNewCategory Done--------------------------------------------------------*/
    public ResponseDTO addNewCategory(@RequestBody Category category) {
        System.out.println("in  add new category");
        Category cat = categoryService.addCategory(category);
        if (cat != null) {
            return new ResponseDTO(true, "Category added successfully");
        }
        throw new UnexpectedErrorException("Error while adding new  category");
    }

    @PutMapping("/update/{cat_id}")/*------------------------------------------Admin updateCategory Done--------------------------------------------------------*/
    public ResponseDTO updateCategory(@RequestBody Category category, @PathVariable String cat_id) {
        System.out.println("in  update category");
        Category cat = categoryService.updateCategory(Integer.parseInt(cat_id), category);
        if (cat != null) {
            return new ResponseDTO(true, "Category Updated successfully");
        }
        throw new UnexpectedErrorException("Error while updating  category");
    }

    @DeleteMapping("/delete/{cat_id}")/*------------------------------------------Admin deleteCategory Done--------------------------------------------------------*/
    public ResponseDTO deleteCategory(@PathVariable String cat_id) {
        System.out.println("in  Delete category");
        return new ResponseDTO(true, categoryService.deleteCategory(Integer.parseInt(cat_id)));
    }

}
