package com.example.demo.automapping;

import com.example.demo.dto.request.PermissionRequest;
import com.example.demo.dto.response.PermissionResponse;
import com.example.demo.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionResponse toPermissionResponse(Permission permission);

    Permission toPermission(PermissionRequest request);

    List<PermissionResponse> toListPermissionResponse(List<Permission> permissions);

    void updatePermission(PermissionRequest request, @MappingTarget Permission permission);
}
