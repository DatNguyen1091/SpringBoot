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
@Table(name = "order")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order extends AudiTableEntity<Long> {
    @Column(nullable = false)
    Double orderTotal;

    @Column(nullable = false)
    Double orderItemTotal;

    @Column(nullable = false)
    Double shippingCharge;

    @Column(nullable = false)
    Long deliveryAddressId;

    @Column(nullable = false)
    String customerId;

    @Column(nullable = false)
    int orderStatus;
}
