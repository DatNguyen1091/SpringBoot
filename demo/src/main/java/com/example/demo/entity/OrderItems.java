package com.example.demo.entity;

import com.example.demo.entity.contracts.AudiTableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@Entity
@Table(name = "orderItems")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItems extends AudiTableEntity<Long> {
    @Column(nullable = false)
    int quantity;

    @Column(nullable = false)
    Long productId;

    @Column(nullable = false)
    Double price;

    @Column(nullable = false)
    Long orderId;
}
