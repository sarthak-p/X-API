package com.cooksys.twitter.mappers;

import com.cooksys.twitter.dtos.ProfileDto;
import com.cooksys.twitter.entities.Profile;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileDto entityToDto(Profile profile);
    Profile dtoToEntity(ProfileDto profileDto);
    List<ProfileDto> entitiesToDtos(List<Profile> profiles);
}
