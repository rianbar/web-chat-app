package com.rian.webchat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GetUserDTO {
    @NotBlank(message = "all fields must be filled!")
    private String nickname;
    @NotBlank(message = "all fields must be filled!")
    private String password;
}
