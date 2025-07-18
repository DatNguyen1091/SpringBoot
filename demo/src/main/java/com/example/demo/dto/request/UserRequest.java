package com.example.demo.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    String userName;

    String password;

    String confirmPassword;

    String fullName;

    String email;

    String phoneNumber;

    String roleId;
}
