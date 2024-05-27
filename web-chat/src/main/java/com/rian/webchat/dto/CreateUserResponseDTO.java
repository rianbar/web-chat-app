package com.rian.webchat.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateUserResponseDTO(@NotBlank int code, @NotBlank String message) {}
