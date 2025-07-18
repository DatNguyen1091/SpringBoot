package com.example.demo.automapping;

import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse toProductResponse(Product product);

    Product toProduct(ProductRequest request);

    List<ProductResponse> toListProductResponse(List<Product> products);

    void updateProduct(ProductRequest request, @MappingTarget Product product);
}
