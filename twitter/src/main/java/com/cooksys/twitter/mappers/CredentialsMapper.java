package com.cooksys.twitter.mappers;

import com.cooksys.twitter.dtos.CredentialsDto;
import com.cooksys.twitter.dtos.ProfileDto;
import com.cooksys.twitter.entities.Credentials;
import com.cooksys.twitter.entities.Profile;

import java.util.List;

public interface CredentialsMapper {
    CredentialsDto entityToDto(Credentials credentials);
    Profile dtoToEntity(CredentialsDto credentialsDto);

    List<CredentialsDto> entitiesToDtos(List<Credentials> credentials);
}
