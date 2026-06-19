package com.harimart.userservice.service;

import com.harimart.userservice.dto.ChangePasswordRequest;
import com.harimart.userservice.dto.LoginRequest;
import com.harimart.userservice.dto.LoginResponse;
import com.harimart.userservice.dto.RegisterRequest;
import com.harimart.userservice.dto.UpdateProfileRequest;
import com.harimart.userservice.dto.UserProfileResponse;
import com.harimart.userservice.dto.UserResponse;

public interface UserService {

    UserResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
    
    UserProfileResponse getCurrentUser(String email);

    UserProfileResponse updateProfile(
            String email,
            UpdateProfileRequest request);

    void changePassword(
            String email,
            ChangePasswordRequest request);
}