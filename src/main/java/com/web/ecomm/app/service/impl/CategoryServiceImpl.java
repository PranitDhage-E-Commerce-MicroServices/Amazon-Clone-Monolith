package com.web.ecomm.app.service.impl;

import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.ResourceNotFoundException;
import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.exceptions.ValidationException;
import com.web.ecomm.app.pojo.Category;
import com.web.ecomm.app.repository.CategoryRepository;
import com.web.ecomm.app.service.ICategoryService;
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
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryRepository categoryRepo;

    @Autowired
    public CategoryServiceImpl(final CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<Category> getAllCategories()
            throws SystemException, BusinessException {

        try {
            return categoryRepo.findAll();
        } catch (Exception e) {
            log.error("Exception While Getting All Categories for Admin - Message: {}", e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public Category getCategoryDetailsById(int catId)
            throws SystemException, BusinessException {

        try {
            return categoryRepo.findById(catId)
                    .orElseThrow(
                            () -> new ResourceNotFoundException(
                                    "Category not found for given category Id : " + catId,
                                    Constants.ERR_RESOURCE_NOT_FOUND
                            )
                    );
        } catch (ResourceNotFoundException e) {
            log.error("Exception While getting Category By Id: {}, Message {}", catId, e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public Category addCategory(Category category)
            throws SystemException, BusinessException, ValidationException {

        try {
            return categoryRepo.save(category);
        } catch (Exception e) {
            log.error("Exception While Saving Category: {}", e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public Category updateCategory(int catId, Category newCategory)
            throws SystemException, BusinessException, ValidationException {

        try {

            Category oldCategory = categoryRepo.findById(catId)
                    .orElseThrow(
                            () -> new ResourceNotFoundException(
                                    "Category not found for given Category Id : " + catId,
                                    Constants.ERR_RESOURCE_NOT_FOUND)
                    );

            if (StringUtils.isNotBlank(newCategory.getCatTitle()))
                oldCategory.setCatTitle(newCategory.getCatTitle());

            if (StringUtils.isNotBlank(newCategory.getCatDescription()))
                oldCategory.setCatDescription(newCategory.getCatDescription());

            return categoryRepo.save(oldCategory);

        } catch (ResourceNotFoundException e) {
            log.error("Exception While Updating Address: {}", e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }

    @Override
    public boolean deleteCategory(int catId)
            throws SystemException, BusinessException {

        if (!categoryRepo.existsById(catId)) {
            throw new ResourceNotFoundException(
                    "Category not found for given Category Id : " + catId,
                    Constants.ERR_RESOURCE_NOT_FOUND);
        }

        categoryRepo.deleteById(catId);
        return true;
    }

    @Override
    public Integer countAllCategory()
            throws SystemException, BusinessException {

        try {
            return categoryRepo.findAll().size();
        } catch (Exception e) {
            log.error("Exception While Getting Count of All Categories: {}", e.getMessage());
            throw new BusinessException(e.getMessage(), Constants.ERR_BUSINESS);
        }
    }
}
