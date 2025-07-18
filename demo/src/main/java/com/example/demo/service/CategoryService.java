package com.example.demo.service;

import com.example.demo.automapping.CategoryMapper;
import com.example.demo.dto.request.CategoryRequest;
import com.example.demo.dto.request.RequestParameter;
import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.shared.ErrorMessage.ValidatorMessage;
import com.example.demo.shared.wrappers.Messages;
import com.example.demo.shared.wrappers.PaginatedResult;
import com.example.demo.shared.wrappers.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService{
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);
    private final CategoryRepository _categoryRepository;
    private final CategoryMapper _categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper){
        _categoryRepository = categoryRepository;
        _categoryMapper = categoryMapper;
    }

    public PaginatedResult<CategoryResponse> getAllCategories(RequestParameter request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize());
        Page<Category> page = _categoryRepository.findAll(pageable);
        List<Category> categories = page.getContent();

        var result = _categoryMapper.toListCategoryResponse(categories);

        int totalRecord = (int)_categoryRepository.count();

        return PaginatedResult.Success(result, totalRecord, request.getPageNumber(), request.getPageSize());
    }

    public Result<CategoryResponse> getCategoryById(Long id) {
        var category = _categoryRepository.findById(id);
        if(category.isEmpty()){
            return Result.fail(new Messages(ValidatorMessage.SYS003I));
        }

        var result = _categoryMapper.toCategoryResponse(category.get());

        return Result.success(result);
    }

    public Result<CategoryResponse> createCategory(CategoryRequest categoryRequest) {
        try{
            Category category = _categoryMapper.toCategory(categoryRequest);
            _categoryRepository.save(category);
            var result = _categoryMapper.toCategoryResponse(category);

            return Result.success(result);
        } catch (Exception e) {
            logger.error("Error: ", e);
            return Result.fail(new Messages(ValidatorMessage.SYS008I));
        }
    }

    public Result<CategoryResponse> updateCategory(Long id, CategoryRequest categoryRequest) {
        try{
            var category = _categoryRepository.findById(id);
            if(category.isEmpty()){
                return Result.fail(new Messages(ValidatorMessage.SYS003I));
            }

            _categoryMapper.updateCategory(categoryRequest, category.get());

            Category categoryUpdate = _categoryRepository.save(category.get());
            var result = _categoryMapper.toCategoryResponse(categoryUpdate);

            return Result.success(result);
        } catch (Exception e) {
            logger.error("Error: ", e);
            return Result.fail(new Messages(ValidatorMessage.SYS008I));
        }
    }

    public Result<Boolean> deleteCategory(Long id) {
        try{
            var categoryOpt = _categoryRepository.findById(id);

            if (categoryOpt.isEmpty()) {
                return Result.fail(new Messages(ValidatorMessage.SYS003I));
            }

            _categoryRepository.delete(categoryOpt.get());

            return Result.success(true);
        } catch (Exception e) {
            logger.error("Error: ", e);
            return Result.fail(new Messages(ValidatorMessage.SYS008I));
        }
    }
}