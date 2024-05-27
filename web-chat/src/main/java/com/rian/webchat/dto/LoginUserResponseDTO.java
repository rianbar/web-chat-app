package com.rian.webchat.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginUserResponseDTO(@NotBlank int code, @NotBlank String token) {
}
