package com.example.demo.automapping;

import com.example.demo.dto.request.RoleRequest;
import com.example.demo.dto.response.RoleResponse;
import com.example.demo.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleResponse toRoleResponse(Role role);

    Role toRole(RoleRequest request);

    List<RoleResponse> toListRoleResponse(List<Role> roles);

    void updateRole(RoleRequest request, @MappingTarget Role role);
}

