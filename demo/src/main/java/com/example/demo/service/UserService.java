package com.example.demo.service;

import com.example.demo.automapping.UserMapper;
import com.example.demo.dto.request.RequestParameter;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.constant.PredefinedRole;
import com.example.demo.shared.ErrorMessage.ValidatorMessage;
import com.example.demo.shared.wrappers.Messages;
import com.example.demo.shared.wrappers.PaginatedResult;
import com.example.demo.shared.wrappers.Result;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository _userRepository;
    private final RoleRepository _roleRepository;
    private final PasswordEncoder _passwordEncoder;
    private final UserMapper _userMapper;
    private final CurrentUserService _currentUserService;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper,
                       PasswordEncoder passwordEncoder, RoleRepository roleRepository,
                       CurrentUserService currentUserService){
        _userRepository = userRepository;
        _passwordEncoder = passwordEncoder;
        _roleRepository = roleRepository;
        _userMapper = userMapper;
        _currentUserService = currentUserService;
    }

    public PaginatedResult<UserResponse> getAllUsers(RequestParameter request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize());
        Page<User> page = _userRepository.findAll(pageable);
        List<User> users = page.getContent();

        var result = _userMapper.toListUserResponse(users);

        int totalRecord = (int)_userRepository.count();

        return PaginatedResult.Success(result, totalRecord, request.getPageNumber(), request.getPageSize());
    }

    public Result<UserResponse> getUserById(String id) {
        var user = _userRepository.findById(id);
        if(user.isEmpty()){
            return Result.fail(new Messages(ValidatorMessage.SYS003I));
        }

        var result = _userMapper.toUserResponse(user.get());

        return Result.success(result);
    }

    public Result<UserResponse> createUser(UserRequest request) {
        try {
            // Validate password match
            if (!request.getPassword().equals(request.getConfirmPassword())) {
                throw new AppException(ErrorCode.INVALID_PASSWORD);
            }

            // Check if username already exists
            if (_userRepository.findByUserName(request.getUserName()).isPresent()) {
                throw new AppException(ErrorCode.USER_EXISTED);
            }

            User user = _userMapper.toUser(request);
            user.setPassword(_passwordEncoder.encode(request.getPassword()));

            Role role;
            if(request.getRoleId() == null || request.getRoleId().isEmpty()){
                role = _roleRepository.findByRoleName(PredefinedRole.USER)
                        .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
            } else {
                role = _roleRepository.findById(request.getRoleId())
                        .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
            }

            user.setRoleId(role.getId());

            user = _userRepository.save(user);

            UserResponse userResponse = _userMapper.toUserResponse(user);
            userResponse.setRoleName(role.getRoleName());

            return Result.success(userResponse);
        } catch (Exception e) {
            logger.error("Error: ", e);
            return Result.fail(new Messages(ValidatorMessage.SYS008I));
        }

    }

    public Result<UserResponse> updateUser(String id, UserRequest request) {
        try {
            String roleName = _currentUserService.getRole();
            String userId = _currentUserService.getUserId();
            if (!roleName.equals(PredefinedRole.ADMIN) && !userId.equals(id)) {
                return Result.fail(new Messages(ValidatorMessage.SYS003I));
            }

            var user = _userRepository.findById(id);
            if(user.isEmpty()){
                return Result.fail(new Messages(ValidatorMessage.SYS003I));
            }

            var role = _roleRepository.findById(request.getRoleId());
            if(role.isEmpty()){
                return Result.fail(new Messages(ValidatorMessage.SYS003I));
            }

            _userMapper.updateUser(request, user.get());

            user.get().setPassword(_passwordEncoder.encode(request.getPassword()));

            User userUpdate = _userRepository.save(user.get());

            UserResponse userResponse = _userMapper.toUserResponse(user.get());
            userResponse.setRoleName(role.get().getRoleName());

            return Result.success(userResponse);
        } catch (Exception e) {
            logger.error("Error: ", e);
            return Result.fail(new Messages(ValidatorMessage.SYS008I));
        }
    }

    public Result<Boolean> deleteUser(String id) {
        try {
            var user = _userRepository.findById(id);
            if(user.isEmpty()){
                return Result.fail(new Messages(ValidatorMessage.SYS003I));
            }
            _userRepository.deleteById(id);
            return Result.success(true);
        } catch (Exception e) {
            logger.error("Error: ", e);
            return Result.fail(new Messages(ValidatorMessage.SYS008I));
        }
    }
}
