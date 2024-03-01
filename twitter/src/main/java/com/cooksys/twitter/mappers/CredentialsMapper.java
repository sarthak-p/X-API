package com.cooksys.twitter.mappers;

import com.cooksys.twitter.dtos.CredentialsDto;
import com.cooksys.twitter.entities.Credentials;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CredentialsMapper {
    CredentialsDto entityToDto(Credentials credentials);

    Credentials dtoToEntity(CredentialsDto credentialsDto);

    List<CredentialsDto> entitiesToDtos(List<Credentials> credentials);
}
