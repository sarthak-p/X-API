package com.cooksys.twitter.services;

import com.cooksys.twitter.dtos.UserResponseDto;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getAllUsers();
}
