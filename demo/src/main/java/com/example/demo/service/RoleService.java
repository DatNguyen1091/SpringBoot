package com.example.demo.service;

import com.example.demo.automapping.RoleMapper;
import com.example.demo.dto.request.RequestParameter;
import com.example.demo.dto.request.RoleRequest;
import com.example.demo.dto.response.RoleResponse;
import com.example.demo.entity.Role;
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

import java.util.List;

@Service
public class RoleService {
    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);
    private final RoleRepository _roleRepository;
    private final RoleMapper _roleMapper;

    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper){
        _roleRepository = roleRepository;
        _roleMapper = roleMapper;
    }

    public PaginatedResult<RoleResponse> getAllRoles(RequestParameter request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize());
        Page<Role> page = _roleRepository.findAll(pageable);
        List<Role> categories = page.getContent();

        var result = _roleMapper.toListRoleResponse(categories);

        int totalRecord = (int)_roleRepository.count();

        return PaginatedResult.Success(result, totalRecord, request.getPageNumber(), request.getPageSize());
    }

    public Result<RoleResponse> getRoleById(String id) {
        var role =  _roleRepository.findById(id);
        if(role.isEmpty()){
            return Result.fail(new Messages(ValidatorMessage.SYS003I));
        }

        var result = _roleMapper.toRoleResponse(role.get());

        return Result.success(result);
    }

    public Result<RoleResponse> createRole(RoleRequest request) {
        try {
            var role = _roleMapper.toRole(request);
            _roleRepository.save(role);
            var result =  _roleMapper.toRoleResponse(role);

            return Result.success(result);
        } catch (Exception e) {
            logger.error("Error: ", e);
            return Result.fail(new Messages(ValidatorMessage.SYS008I));
        }
    }

    public Result<Boolean> deleteRole(String id) {
        try {
            var role = _roleRepository.findById(id);
            if(role.isEmpty()){
                return Result.fail(new Messages(ValidatorMessage.SYS003I));
            }
            _roleRepository.delete(role.get());

            return Result.success(true);
        } catch (Exception e) {
            logger.error("Error: ", e);
            return Result.fail(new Messages(ValidatorMessage.SYS008I));
        }
    }
}
