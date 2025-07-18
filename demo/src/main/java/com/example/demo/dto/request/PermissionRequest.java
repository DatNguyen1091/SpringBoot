package com.example.demo.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionRequest {
    Long id;

    String resourceName;

    String roleId;

    boolean isCanCreate;

    boolean isCanRead;

    boolean isCanUpdate;

    boolean isCanDelete;
}
