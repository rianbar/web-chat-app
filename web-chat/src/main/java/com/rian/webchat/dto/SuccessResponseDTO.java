package com.rian.webchat.dto;

import jakarta.validation.constraints.NotBlank;

public record SuccessResponseDTO(@NotBlank int code, @NotBlank String status) {}
