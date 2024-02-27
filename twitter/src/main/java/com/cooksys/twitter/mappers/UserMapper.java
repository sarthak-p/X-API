package com.cooksys.twitter.mappers;

import com.cooksys.twitter.dtos.UserRequestDto;
import com.cooksys.twitter.dtos.UserResponseDto;
import com.cooksys.twitter.entities.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User requestDtoToEntity(UserRequestDto userRequestDto);
    UserResponseDto entityToResponseDto(User user);
    List<UserResponseDto> entitiesToResponseDtos(List<User> users);
}
