package com.example.demo.controller;

import com.example.demo.dto.request.RequestParameter;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.service.UserService;
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
@RequestMapping("/api/user")
@Tag(name = "User Controller", description = "User management APIs")
public class UserController {
    private final UserService _userService;

    @Autowired
    public  UserController(UserService userService){
        _userService = userService;
    }

    @PreAuthorize("hasAuthority('USER_VIEW')")
    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<PaginatedResult<UserResponse>> getAll(RequestParameter request) {
        return ResponseEntity.ok(_userService.getAllUsers(request));
    }

    @PreAuthorize("hasAuthority('USER_VIEW')")
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    public ResponseEntity<Result<UserResponse>> getById(@PathVariable String id) {
        return ResponseEntity.ok(_userService.getUserById(id));
    }

    @PostMapping
    @Operation(summary = "Create user")
    public ResponseEntity<Result<UserResponse>> createUser(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(_userService.createUser(request));
    }

    @PreAuthorize("hasAuthority('USER_UPDATE')")
    @PutMapping("/{id}")
    @Operation(summary = "Update user by ID")
    public ResponseEntity<Result<UserResponse>> updateUser(@Valid @PathVariable String id, @RequestBody UserRequest request) {
        return ResponseEntity.ok(_userService.updateUser(id, request));
    }

    @PreAuthorize("hasAuthority('USER_DELETE')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by ID")
    public ResponseEntity<Result<Boolean>> deleteUser(@PathVariable String id) {
        return ResponseEntity.ok(_userService.deleteUser(id));
    }
}
