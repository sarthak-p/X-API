package com.cooksys.twitter.services;

import com.cooksys.twitter.dtos.CredentialsDto;
import com.cooksys.twitter.dtos.UserRequestDto;
import com.cooksys.twitter.dtos.UserResponseDto;
import com.cooksys.twitter.entities.User;

import java.util.List;

public interface UserService {
    User getUser(Long id);

    List<UserResponseDto> getAllUsers();
    UserResponseDto createUser(UserRequestDto userRequestDto);

    UserResponseDto getUserByUsername(String username);

    UserResponseDto deleteUser(String username, CredentialsDto credentialsDto);
}
