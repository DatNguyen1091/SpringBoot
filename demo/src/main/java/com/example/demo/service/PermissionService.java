package com.example.demo.service;

import com.example.demo.automapping.PermissionMapper;
import com.example.demo.dto.request.PermissionRequest;
import com.example.demo.dto.request.RequestParameter;
import com.example.demo.dto.response.PermissionResponse;
import com.example.demo.entity.Permission;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.shared.ErrorMessage.ValidatorMessage;
import com.example.demo.shared.wrappers.Messages;
import com.example.demo.shared.wrappers.PaginatedResult;
import com.example.demo.shared.wrappers.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.demo.constant.PredefinedRole;


import java.util.List;

@Service
public class PermissionService {
    private static final Logger logger = LoggerFactory.getLogger(PermissionService.class);
    private final PermissionRepository _permissionRepository;
    private final PermissionMapper _permissionMapper;
    private final CurrentUserService _currentUserService;
    private final RoleRepository _roleRepository;

    public PermissionService(PermissionRepository permissionRepository, PermissionMapper permissionMapper,
                             CurrentUserService currentUserService, RoleRepository roleRepository) {
        _permissionRepository = permissionRepository;
        _permissionMapper = permissionMapper;
        _currentUserService = currentUserService;
        _roleRepository = roleRepository;
    }

    public PaginatedResult<PermissionResponse> getAllPermissions(RequestParameter request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize());
        Page<Permission> page = _permissionRepository.findAll(pageable);
        List<Permission> categories = page.getContent();

        var result = _permissionMapper.toListPermissionResponse(categories);

        int totalRecord = (int)_permissionRepository.count();

        return PaginatedResult.Success(result, totalRecord, request.getPageNumber(), request.getPageSize());
    }

    public Result<List<PermissionResponse>> getPermissionsByRoleId(String roleId) {
        String currentRole = _currentUserService.getRole();

        var role = _roleRepository.findById(roleId);
        if(role.isEmpty()){
            return Result.fail(new Messages(ValidatorMessage.SYS003I));
        }

        if (!currentRole.equals(role.get().getRoleName()) && !currentRole.equals(PredefinedRole.ADMIN)) {
            return Result.fail(new Messages(ValidatorMessage.SYS002I));
        }

        List<Permission> permissions = _permissionRepository.findByRoleId(roleId);

        var result = _permissionMapper.toListPermissionResponse(permissions);

        return Result.success(result);
    }

    public Result<PermissionResponse> createPermission(PermissionRequest request) {
        try {
            Permission permission = _permissionMapper.toPermission(request);
            _permissionRepository.save(permission);

            var result = _permissionMapper.toPermissionResponse(permission);

            return Result.success(result);
        } catch (Exception e) {
            logger.error("Error: ", e);
            return Result.fail(new Messages(ValidatorMessage.SYS008I));
        }
    }

    public Result<PermissionResponse> updatePermission(PermissionRequest request) {
        try {
            var existingPermission = _permissionRepository.findById(request.getId());
            if(existingPermission.isEmpty()){
                return Result.fail(new Messages(ValidatorMessage.SYS003I));
            }

            _permissionMapper.updatePermission(request, existingPermission.get());

            _permissionRepository.save(existingPermission.get());

            var result = _permissionMapper.toPermissionResponse(existingPermission.get());

            return Result.success(result);
        } catch (Exception e) {
            logger.error("Error: ", e);
            return Result.fail(new Messages(ValidatorMessage.SYS008I));
        }
    }

    public Result<Boolean> deletePermission(Long id) {
        try {
            var permission = _permissionRepository.findById(id);
            if(permission.isEmpty()){
                return Result.fail(new Messages(ValidatorMessage.SYS003I));
            }
            _permissionRepository.delete(permission.get());

            return Result.success(true);
        } catch (Exception e) {
            logger.error("Error: ", e);
            return Result.fail(new Messages(ValidatorMessage.SYS008I));
        }
    }
}
