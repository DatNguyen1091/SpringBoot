package com.example.demo.controller;

import com.example.demo.dto.request.PermissionRequest;
import com.example.demo.dto.request.RequestParameter;
import com.example.demo.dto.response.PermissionResponse;
import com.example.demo.service.PermissionService;
import com.example.demo.shared.wrappers.PaginatedResult;
import com.example.demo.shared.wrappers.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@Tag(name = "Permission Controller", description = "Permission management APIs")
public class PermissionController {
    private final PermissionService _permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService){
        _permissionService = permissionService;
    }

    @GetMapping
    @Operation(summary = "Get all permissions")
    public ResponseEntity<PaginatedResult<PermissionResponse>> getAll(RequestParameter request) {
        return ResponseEntity.ok(_permissionService.getAllPermissions(request));
    }

    @PreAuthorize("hasAuthority('PERMISSION_VIEW')")
    @GetMapping("/{id}")
    @Operation(summary = "Get permissions by ID")
    public ResponseEntity<Result<List<PermissionResponse>>> getById(@PathVariable String id) {
        return ResponseEntity.ok(_permissionService.getPermissionsByRoleId(id));
    }

    @PreAuthorize("hasAuthority('PERMISSION_CREATE')")
    @PostMapping
    @Operation(summary = "Create permission")
    public ResponseEntity<Result<PermissionResponse>> post(@Valid @RequestBody PermissionRequest request) {
        return ResponseEntity.ok(_permissionService.createPermission(request));
    }

    @PreAuthorize("hasAuthority('PERMISSION_UPDATE')")
    @PutMapping
    @Operation(summary = "Create permission")
    public ResponseEntity<Result<PermissionResponse>> put(@Valid @RequestBody PermissionRequest request) {
        return ResponseEntity.ok(_permissionService.updatePermission(request));
    }

    @PreAuthorize("hasAuthority('PERMISSION_DELETE')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete permission by ID")
    public ResponseEntity<Result<Boolean>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(_permissionService.deletePermission(id));
    }
}