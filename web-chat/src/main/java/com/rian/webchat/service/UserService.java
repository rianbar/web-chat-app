package com.rian.webchat.service;

import com.rian.webchat.dto.PostUserDTO;
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

    public UserModel saveUserService(PostUserDTO dto) {
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
}
