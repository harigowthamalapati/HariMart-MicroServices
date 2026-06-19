package com.harimart.userservice.dto;

import com.harimart.userservice.entity.Role;

public record UserResponse(

        Long id,
        String firstName,
        String lastName,
        String email,
        Role role

) {}