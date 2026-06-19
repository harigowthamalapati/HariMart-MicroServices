package com.harimart.userservice.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harimart.userservice.dto.ChangePasswordRequest;
import com.harimart.userservice.dto.UpdateProfileRequest;
import com.harimart.userservice.dto.UserProfileResponse;
import com.harimart.userservice.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor; 

@RestController
@RequestMapping("/HMart/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    
    @GetMapping("/me")
    public UserProfileResponse currentUser(
            Principal principal) {

        return userService.getCurrentUser(
                principal.getName());
    }

    @PutMapping("/me")
    public UserProfileResponse updateProfile(
            Principal principal,
            @Valid @RequestBody
            UpdateProfileRequest request) {

        return userService.updateProfile(
                principal.getName(),
                request);
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            Principal principal,
            @Valid @RequestBody
            ChangePasswordRequest request) {

        userService.changePassword(
                principal.getName(),
                request);

        return ResponseEntity.ok(
                "Password changed successfully");
    }
}