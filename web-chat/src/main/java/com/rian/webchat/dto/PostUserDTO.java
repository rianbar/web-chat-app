package com.rian.webchat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostUserDTO {
    @NotBlank
    private String nickname;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
