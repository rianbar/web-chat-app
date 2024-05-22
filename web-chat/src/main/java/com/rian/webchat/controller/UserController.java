package com.rian.webchat.controller;

import com.rian.webchat.dto.GetUserDTO;
import com.rian.webchat.dto.PostUserDTO;
import com.rian.webchat.dto.SuccessResponseDTO;
import com.rian.webchat.model.UserModel;
import com.rian.webchat.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserModel>> getAllUsersPath() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @PostMapping("/login")
    public ResponseEntity<Object> getUserByNicknamePath(@RequestBody @Valid GetUserDTO dto) {
        userService.getUserByNicknameService(dto);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponseDTO(HttpStatus.OK.value()
                , "success"));
    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveUserPath(@RequestBody @Valid PostUserDTO dto) {
        userService.saveUserService(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponseDTO(HttpStatus.CREATED.value()
                , "success"));
    }
}
