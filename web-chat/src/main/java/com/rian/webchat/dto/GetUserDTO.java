package com.rian.webchat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GetUserDTO {
    @NotBlank
    private String nickname;
    @NotBlank
    private String password;
}
