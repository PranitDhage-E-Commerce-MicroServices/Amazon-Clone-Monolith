package com.web.ecomm.app.service;

import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.exceptions.ValidationException;
import com.web.ecomm.app.pojo.Category;

import java.util.List;

public interface ICategoryService {

    /**
     * Get all the Categories for Admin
     *
     * @return List of Categories for Admin
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.SystemException SystemException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     */
    List<Category> getAllCategories() throws SystemException, BusinessException;;

    /**
     * Get Category Details By Id for Admin
     *
     * @return List of Category Details By Id for Admin
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.SystemException SystemException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     */
    Category getCategoryDetailsById(int catId)throws SystemException, BusinessException;

    /**
     * Saves new Category by Admin
     *
     * @param category Category Request Body
     * @return Saved Category
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     * @throws com.web.ecomm.app.exceptions.SystemException SystemException
     */
    Category addCategory(Category category)throws SystemException, BusinessException, ValidationException;

    /**
     * Updates Category for given Category Id
     *
     * @param catId Category Identifier
     * @param category Category Request Body
     * @return Updated Category
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     * @throws com.web.ecomm.app.exceptions.ValidationException ValidationException
     * @throws com.web.ecomm.app.exceptions.SystemException SystemException
     */
    Category updateCategory(int catId, Category category)throws SystemException, BusinessException, ValidationException;

    /**
     * Deletes Category for given Category Id
     *
     * @param catId Category Identifier
     * @return Deleted Category Status
     * @throws com.web.ecomm.app.exceptions.BusinessException BusinessException
     */
    boolean deleteCategory(int catId)throws SystemException, BusinessException;

    Integer countAllCategory()throws SystemException, BusinessException;


}
