package com.example.demo.service;

import com.example.demo.automapping.ProductMapper;
import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.request.RequestParameter;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.entity.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
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
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository _productRepository;
    private final CategoryRepository _categoryRepository;
    private final ProductMapper _productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper,
                          CategoryRepository categoryRepository){
        _productRepository = productRepository;
        _categoryRepository = categoryRepository;
        _productMapper = productMapper;
    }

    public PaginatedResult<ProductResponse> getAllProducts(RequestParameter request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize());
        Page<Product> page = _productRepository.findAll(pageable);
        List<Product> categories = page.getContent();

        var result = _productMapper.toListProductResponse(categories);

        int totalRecord = (int)_productRepository.count();

        return PaginatedResult.Success(result, totalRecord, request.getPageNumber(), request.getPageSize());
    }

    public Result<ProductResponse> getProductById(Long id) {
        var product = _productRepository.findById(id);
        if(product.isEmpty()){
            return Result.fail(new Messages(ValidatorMessage.SYS003I));
        }

        var result = _productMapper.toProductResponse(product.get());

        return Result.success(result);
    }

    public Result<ProductResponse> createProduct(ProductRequest productRequest) {
        try{
            var category = _categoryRepository.findById(productRequest.getCategoryId());
            if(category.isEmpty()){
                return Result.fail(new Messages(ValidatorMessage.SYS003I));
            }
            Product product = _productMapper.toProduct(productRequest);
            _productRepository.save(product);
            var result = _productMapper.toProductResponse(product);

            return Result.success(result);
        } catch (Exception e) {
            logger.error("Error: ", e);
            return Result.fail(new Messages(ValidatorMessage.SYS008I));
        }
    }

    public Result<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {
        try{
            var product = _productRepository.findById(id);
            if(product.isEmpty()){
                return Result.fail(new Messages(ValidatorMessage.SYS003I));
            }

            var category = _categoryRepository.findById(productRequest.getCategoryId());
            if(category.isEmpty()){
                return Result.fail(new Messages(ValidatorMessage.SYS003I));
            }

            _productMapper.updateProduct(productRequest, product.get());
            _productRepository.save(product.get());

            var result = _productMapper.toProductResponse(product.get());

            return Result.success(result);
        } catch (Exception e) {
            logger.error("Error: ", e);
            return Result.fail(new Messages(ValidatorMessage.SYS008I));
        }
    }

    public Result<Boolean> deleteProduct(Long id) {
        try{
            var product = _productRepository.findById(id);
            if(product.isEmpty()){
                return Result.fail(new Messages(ValidatorMessage.SYS003I));
            }
            _productRepository.delete(product.get());

            return Result.success(true);
        } catch (Exception e) {
            logger.error("Error: ", e);
            return Result.fail(new Messages(ValidatorMessage.SYS008I));
        }

    }
}