package com.example.demoparkapi.resources.controllers;

import com.example.demoparkapi.entities.User;
import com.example.demoparkapi.resources.dto.UserPasswordDto;
import com.example.demoparkapi.resources.dto.UserRequestDto;
import com.example.demoparkapi.resources.dto.UserResponseDto;
import com.example.demoparkapi.resources.dto.mapper.UserMapper;
import com.example.demoparkapi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserRequestDto requestDto){
        User user = userService.save(UserMapper.toUser(requestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user));
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id){
        User user = userService.findById(id);
        return ResponseEntity.ok(UserMapper.toDto(user));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id,@Valid @RequestBody UserPasswordDto dto){
        User user = userService.editPassword(id, dto.getCurrentPassword(), dto.getNewPassword(), dto.getConfirmPassword());
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll(){
        List<User> users = userService.findAll();
        return ResponseEntity.ok(UserMapper.toListDto(users));
    }

}
