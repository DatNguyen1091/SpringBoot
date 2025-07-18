package com.example.demo.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    @NotNull(message = "Product name cannot be empty!")
    @Size(max = 100, message = "Product name must be no longer than 100 characters!")
    String name;

    String description;

    @NotNull(message = "Product price cannot be empty!")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price cannot be less than 0!")
    double price;

    Long categoryId;
}
