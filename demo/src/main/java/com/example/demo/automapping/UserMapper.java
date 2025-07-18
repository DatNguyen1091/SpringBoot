package com.example.demo.automapping;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);

    User toUser(UserRequest request);

    List<UserResponse> toListUserResponse(List<User> user);

    void updateUser(UserRequest request, @MappingTarget User user);
}
