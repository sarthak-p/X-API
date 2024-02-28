package com.cooksys.twitter.controllers;

import com.cooksys.twitter.dtos.UserResponseDto;
import com.cooksys.twitter.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping
    public List<UserResponseDto> getAllUsers(){
        return userService.getAllUsers();
    }

}
