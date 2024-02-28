package com.cooksys.twitter.mappers;

import com.cooksys.twitter.dtos.UserRequestDto;
import com.cooksys.twitter.dtos.UserResponseDto;
import com.cooksys.twitter.entities.User;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = { ProfileMapper.class, CredentialsMapper.class })
public interface UserMapper {
  // @Mapping(target = "username", source = "user.credentials.username")
  UserResponseDto entityToResponseDto(User user);

  User requestDtoToEntity(UserRequestDto userRequestDto);

  List<UserResponseDto> entitiesToResponseDtos(List<User> users);

  @AfterMapping
  default void setUsernameFromCredentials(User user, @MappingTarget UserResponseDto dto) {
    if (user.getCredentials() != null) {
      dto.setUsername(user.getCredentials().getUsername());
    }
  }
}
