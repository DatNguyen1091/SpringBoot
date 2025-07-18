package com.example.demo.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionResponse {
    Long id;

    String resourceName;

    String roleId;

    boolean isCanCreate;

    boolean isCanRead;

    boolean isCanUpdate;

    boolean isCanDelete;
}
