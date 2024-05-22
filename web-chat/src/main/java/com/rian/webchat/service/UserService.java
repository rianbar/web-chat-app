package com.rian.webchat.service;

import com.rian.webchat.dto.GetUserDTO;
import com.rian.webchat.dto.PostUserDTO;
import com.rian.webchat.errors.InvalidCredentialsException;
import com.rian.webchat.errors.UserAlreadyExistsException;
import com.rian.webchat.errors.UserNotFoundException;
import com.rian.webchat.model.UserModel;
import com.rian.webchat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<UserModel> getAllUsers() {
        return repository.findAll();
    }

    public void getUserByNicknameService(GetUserDTO dto) {
        var user = repository.findByNickname(dto.getNickname());
        if (user.isPresent()) {
            if (!matchUserPasswords(user.get().getPassword(), dto.getPassword()))
                throw new InvalidCredentialsException("your password is wrong!");
        } else {
            throw new UserNotFoundException("user doesn't exists!");
        }
    }

    public UserModel saveUserService(PostUserDTO dto) {
        if (repository.findByNickname(dto.getNickname()).isPresent()) {
            throw new UserAlreadyExistsException("user already exists, try another!");
        }
        var userToSave = parseDTO(dto);
        return repository.save(userToSave);
    }

    private UserModel parseDTO(PostUserDTO dto) {
        return UserModel.builder()
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .password(dto.getPassword())
                .build();
    }

    private boolean matchUserPasswords(String uPassword, String dPassword) {
        return uPassword.equalsIgnoreCase(dPassword);
    }
}
