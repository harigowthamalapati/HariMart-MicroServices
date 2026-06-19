package com.harimart.userservice.service.impl;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.harimart.userservice.dto.ChangePasswordRequest;
import com.harimart.userservice.dto.LoginRequest;
import com.harimart.userservice.dto.LoginResponse;
import com.harimart.userservice.dto.RegisterRequest;
import com.harimart.userservice.dto.UpdateProfileRequest;
import com.harimart.userservice.dto.UserProfileResponse;
import com.harimart.userservice.dto.UserResponse;
import com.harimart.userservice.entity.Role;
import com.harimart.userservice.entity.User;
import com.harimart.userservice.exception.EmailAlreadyExistsException;
import com.harimart.userservice.exception.InvalidCredentialsException;
import com.harimart.userservice.exception.ResourceNotFoundException;
import com.harimart.userservice.repository.UserRepository;
import com.harimart.userservice.security.JwtService;
import com.harimart.userservice.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    

    @Override
    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(
                    "Email already registered : " + request.email());
        }

        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.ROLE_CUSTOMER)
                .isActive(true)
                .emailVerified(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }
    
    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() ->
                        new InvalidCredentialsException(
                                "Invalid email or password"));

        boolean matches =
                passwordEncoder.matches(
                        request.password(),
                        user.getPassword());

        if (!matches) {
            throw new InvalidCredentialsException(
                    "Invalid email or password");
        }

        String token =
                jwtService.generateToken(user.getEmail());

        return new LoginResponse(
                token,
                user.getEmail(),
                user.getRole().name()
        );
    }
    
    @Override
    public UserProfileResponse getCurrentUser(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return new UserProfileResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }
    
    @Override
    public UserProfileResponse updateProfile(
            String email,
            UpdateProfileRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());

        User updatedUser = userRepository.save(user);

        return new UserProfileResponse(
                updatedUser.getId(),
                updatedUser.getFirstName(),
                updatedUser.getLastName(),
                updatedUser.getEmail(),
                updatedUser.getRole()
        );
    }
    
    @Override
    public void changePassword(
            String email,
            ChangePasswordRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        boolean matches =
                passwordEncoder.matches(
                        request.oldPassword(),
                        user.getPassword());

        if (!matches) {
            throw new InvalidCredentialsException(
                    "Old password is incorrect");
        }

        user.setPassword(
                passwordEncoder.encode(
                        request.newPassword()));

        userRepository.save(user);
    }
    
}