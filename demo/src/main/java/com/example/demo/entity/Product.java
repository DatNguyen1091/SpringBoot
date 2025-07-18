package com.example.demo.entity;

import com.example.demo.entity.contracts.AudiTableEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product extends AudiTableEntity<Long> {
    @Column(nullable = false)
    String name;

    @Column(length = 1000)
    String description;

    String ImageUrl;

    @Column(nullable = false)
    Double price;

    @Column(nullable = false)
    int quantity;

    @Column(nullable = false)
    Long brandId;

    int productStatus;

    boolean isFeatured;

    boolean isBestseller;
}