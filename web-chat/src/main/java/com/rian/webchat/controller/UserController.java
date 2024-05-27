package com.rian.webchat.controller;

import com.rian.webchat.dto.GetUserDTO;
import com.rian.webchat.dto.PostUserDTO;
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
    public ResponseEntity<Object> loginUserPath(@RequestBody @Valid GetUserDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.loginUserService(dto));
    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveUserPath(@RequestBody @Valid PostUserDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUserService(dto));
    }
}
