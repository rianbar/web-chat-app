package com.rian.webchat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PostUserDTO {
    @NotBlank(message = "all fields must be filled!")
    private String nickname;
    @NotBlank(message = "all fields must be filled!")
    @Pattern(regexp=".+@.+\\.[a-z]+", message="Invalid email address!")
    private String email;
    @NotBlank(message = "all fields must be filled!")
    private String password;
}
