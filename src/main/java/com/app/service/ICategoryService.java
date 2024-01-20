package com.app.service;

import com.app.pojo.Category;

import java.util.List;

public interface ICategoryService {

    List<Category> getAllCategories();

    Category addCategory(Category category);

    Category updateCategory(int cat_id, Category category);

    String deleteCategory(int cat_id);

    Integer countAllCategory();

    Category getCategoryDetailsById(int catId);
}
