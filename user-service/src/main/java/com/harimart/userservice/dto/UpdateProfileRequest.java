package com.harimart.userservice.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateProfileRequest(

        @NotBlank
        String firstName,

        @NotBlank
        String lastName

) {
}