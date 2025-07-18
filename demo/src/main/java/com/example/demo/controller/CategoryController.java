package com.example.demo.controller;

import com.example.demo.dto.request.CategoryRequest;
import com.example.demo.dto.request.RequestParameter;
import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.service.CategoryService;
import com.example.demo.shared.wrappers.Result;
import com.example.demo.shared.wrappers.PaginatedResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Category Controller", description = "Category management APIs")
public class CategoryController {
    private final CategoryService _categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        _categoryService = categoryService;
    }

    @GetMapping
    @Operation(summary = "Get all categories")
    public ResponseEntity<PaginatedResult<CategoryResponse>> getAll(RequestParameter requestParameter) {
        return ResponseEntity.ok(_categoryService.getAllCategories(requestParameter));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID")
    public ResponseEntity<Result<CategoryResponse>> getById(@PathVariable Long id) {
        var result = _categoryService.getCategoryById(id);
        return ResponseEntity.ok(result);
    }

    //@PreAuthorize("hasAuthority('CATEGORY_CREATE')")
    @PostMapping
    @Operation(summary = "Create category")
    public ResponseEntity<Result<CategoryResponse>> create(@Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(_categoryService.createCategory(request));
    }

    //@PreAuthorize("hasAuthority('CATEGORY_UPDATE')")
    @PutMapping("/{id}")
    @Operation(summary = "Update category by ID")
    public ResponseEntity<Result<CategoryResponse>> update(@Valid @PathVariable Long id, @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(_categoryService.updateCategory(id, request));
    }

    //@PreAuthorize("hasAuthority('CATEGORY_DELETE')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category by ID")
    public ResponseEntity<Result<Boolean>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(_categoryService.deleteCategory(id));
    }
}