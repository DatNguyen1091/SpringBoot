package com.example.demo.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;

    String userName;

    String password;

    String fullName;

    String email;

    String phoneNumber;

    String roleId;

    String roleName;
}
