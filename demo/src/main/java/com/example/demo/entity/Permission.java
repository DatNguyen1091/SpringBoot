package com.example.demo.entity;

import com.example.demo.entity.contracts.AudiTableEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Permission extends AudiTableEntity<Long> {
    String resourceName;

    String roleId;

    boolean isCanCreate;

    boolean isCanRead;

    boolean isCanUpdate;

    boolean isCanDelete;
}
