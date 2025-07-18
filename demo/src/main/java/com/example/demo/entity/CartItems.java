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
@Table(name = "cartItems")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItems extends AudiTableEntity<Long> {
    @Column(nullable = false)
    Long cartId;

    @Column(nullable = false)
    Long productId;

    @Column(nullable = false)
    int quantity;
}
