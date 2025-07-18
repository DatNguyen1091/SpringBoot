package com.example.demo.controller;

import com.example.demo.dto.request.RequestParameter;
import com.example.demo.dto.request.RoleRequest;
import com.example.demo.dto.response.RoleResponse;
import com.example.demo.service.RoleService;
import com.example.demo.shared.wrappers.PaginatedResult;
import com.example.demo.shared.wrappers.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@Tag(name = "Role Controller", description = "Role management APIs")
public class RoleController {
    private final RoleService _roleService;

    @Autowired
    public RoleController(RoleService roleService){
        _roleService = roleService;
    }


    @GetMapping
    @Operation(summary = "Get all roles")
    public ResponseEntity<PaginatedResult<RoleResponse>> getAll(RequestParameter request) {
        return ResponseEntity.ok(_roleService.getAllRoles(request));
    }

    @PreAuthorize("hasAuthority('ROLE_VIEW')")
    @GetMapping("/{id}")
    @Operation(summary = "Get role by ID")
    public ResponseEntity<Result<RoleResponse>> getById(@PathVariable String id) {
        return ResponseEntity.ok(_roleService.getRoleById(id));
    }

    @PostMapping
    @Operation(summary = "Create role")
    public ResponseEntity<Result<RoleResponse>> post(@Valid @RequestBody RoleRequest request) {
        return ResponseEntity.ok(_roleService.createRole(request));
    }

    @PreAuthorize("hasAuthority('ROLE_DELETE')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete role by ID")
    public ResponseEntity<Result<Boolean>> delete(@PathVariable String id) {
        return ResponseEntity.ok(_roleService.deleteRole(id));
    }
}
