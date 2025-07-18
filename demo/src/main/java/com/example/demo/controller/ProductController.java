package com.example.demo.controller;

import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.request.RequestParameter;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.service.ProductService;
import com.example.demo.shared.wrappers.PaginatedResult;
import com.example.demo.shared.wrappers.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Controller", description = "Product management APIs")
public class ProductController {
    private final ProductService _productService;

    @Autowired
    public ProductController(ProductService productService){
        _productService = productService;
    }

    @GetMapping
    @Operation(summary = "Get all products")
    public ResponseEntity<PaginatedResult<ProductResponse>> getAll(RequestParameter request) {
        return ResponseEntity.ok(_productService.getAllProducts(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID")
    public ResponseEntity<Result<ProductResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(_productService.getProductById(id));
    }

    @PreAuthorize("hasAuthority('PRODUCT_CREATE')")
    @PostMapping
    @Operation(summary = "Create product")
    public ResponseEntity<Result<ProductResponse>> createProduct(@Valid  @RequestBody ProductRequest request) {
        return ResponseEntity.ok(_productService.createProduct(request));
    }

    @PreAuthorize("hasAuthority('PRODUCT_UPDATE')")
    @PutMapping("/{id}")
    @Operation(summary = "Update product by ID")
    public ResponseEntity<Result<ProductResponse>> updateProduct(@Valid @PathVariable Long id, @RequestBody ProductRequest request) {
        return ResponseEntity.ok(_productService.updateProduct(id, request));
    }

    @PreAuthorize("hasAuthority('PRODUCT_DELETE')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product by ID")
    public ResponseEntity<Result<Boolean>> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(_productService.deleteProduct(id));
    }
}