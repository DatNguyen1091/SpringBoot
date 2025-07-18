package com.example.demo.entity.contracts;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AudiTableEntity<TId> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    TId id;
    String createdBy;
    LocalDateTime createdOn;
    String lastModifiedBy;
    LocalDateTime lastModifiedOn;
    boolean isDeleted;
}
