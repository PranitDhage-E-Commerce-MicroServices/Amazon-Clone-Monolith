package com.web.ecomm.app.service;

import com.web.ecomm.app.dao.CategoryRepository;
import com.web.ecomm.app.exceptions.ResourceNotFoundException;
import com.web.ecomm.app.pojo.Category;
import com.web.ecomm.app.utils.Constants;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    CategoryRepository categoryRepo;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public Category updateCategory(int cat_id, Category newCategory) {
        if (categoryRepo.existsById(cat_id)) {
            Category oldCategory = categoryRepo.findById(cat_id).get();
            if (newCategory.getCatTitle() != "") oldCategory.setCatTitle(newCategory.getCatTitle());
            if (newCategory.getCatDescription() != "") oldCategory.setCatDescription(newCategory.getCatDescription());
            return categoryRepo.save(oldCategory);
        }
        throw new ResourceNotFoundException("Category not found for given cat Id : " + cat_id, Constants.ERR_RESOURCE_NOT_FOUND);
    }

    @Override
    public String deleteCategory(int cat_id) {
        if (categoryRepo.existsById(cat_id)) {
            categoryRepo.deleteById(cat_id);
            return "Category Deleted Successfully";
        }
        throw new ResourceNotFoundException("Category not found for given cat Id : " + cat_id, Constants.ERR_RESOURCE_NOT_FOUND);
    }

    @Override
    public Integer countAllCategory() {
        return categoryRepo.findAll().size();
    }

    @Override
    public Category getCategoryDetailsById(int catId) {
        return categoryRepo.findById(catId).orElseThrow(() -> new ResourceNotFoundException("Category not found for given category Id : " + catId, Constants.ERR_RESOURCE_NOT_FOUND));
    }
}
