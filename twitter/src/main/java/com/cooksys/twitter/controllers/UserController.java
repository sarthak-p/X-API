package com.cooksys.twitter.controllers;


import com.cooksys.twitter.dtos.CredentialsDto;
import com.cooksys.twitter.dtos.UserRequestDto;
import com.cooksys.twitter.dtos.UserResponseDto;
import com.cooksys.twitter.repositories.UserRepository;
import com.cooksys.twitter.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private final UserRepository userRepository;

    @GetMapping
    public List<UserResponseDto> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto){
        return userService.createUser(userRequestDto);
    }
    @GetMapping("@{username}")
    public UserResponseDto getUserByUsername(@PathVariable String username){
        return userService.getUserByUsername(username);
    }

    @DeleteMapping("@{username}")
    public UserResponseDto deleteUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto){
        return userService.deleteUser(username, credentialsDto);
    }

    @PatchMapping("@{username}")
    public UserResponseDto updateUser(@PathVariable String username, @RequestBody UserRequestDto userRequestDto){
        return userService.updateUser(username, userRequestDto);
    }

    @PostMapping("@{username}/follow")
    public void followUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto){
        userService.followUser(username, credentialsDto);
    }

    @GetMapping("@{username}/followers")
    public List<UserResponseDto> getFollowers(@PathVariable String username){
        return userService.getFollowers(username);
    }
    @GetMapping("@{username}/following")
    public List<UserResponseDto> getFollowing(@PathVariable String username){
        return userService.getFollowing(username);
    }
    @PostMapping("@{username}/unfollow")
    public void unfollowUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto){
        userService.unfollowUser(username, credentialsDto);
    }
}
