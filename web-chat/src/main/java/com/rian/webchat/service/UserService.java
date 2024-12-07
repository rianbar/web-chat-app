package com.rian.webchat.service;

import com.rian.webchat.configuration.TokenService;
import com.rian.webchat.dto.*;
import com.rian.webchat.errors.InvalidCredentialsException;
import com.rian.webchat.errors.UserAlreadyExistsException;
import com.rian.webchat.errors.UserNotFoundException;
import com.rian.webchat.model.UserModel;
import com.rian.webchat.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final TokenService tokenService;

    public List<UserModel> getAllUsers() {
        return repository.findAll();
    }

    public Object loginUserService(GetUserDTO dto) {
        var user = repository.findByNickname(dto.getNickname());
        if (user.isPresent()) {
            if (!matchUserPasswords(user.get().getPassword(), dto.getPassword()))
                throw new InvalidCredentialsException("your password is wrong!");
            String token = tokenService.generateToken(user.get());
            return new LoginUserResponseDTO(HttpStatus.OK.value(), token);
        } else {
            throw new UserNotFoundException("user doesn't exists!");
        }
    }

    public Object saveUserService(PostUserDTO dto) {
        if (repository.findByNickname(dto.getNickname()).isPresent()) {
            throw new UserAlreadyExistsException("nickname already exists, try another!");
        }
        var userToSave = parseToModel(dto);
        repository.save(userToSave);
        return new CreateUserResponseDTO(HttpStatus.CREATED.value(), "success");
    }

    private UserModel parseToModel(PostUserDTO dto) {
        return UserModel.builder()
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .password(dto.getPassword())
                .role(UserRole.USER)
                .build();
    }

    private boolean matchUserPasswords(String uPassword, String dPassword) {
        return uPassword.equalsIgnoreCase(dPassword);
    }
}
