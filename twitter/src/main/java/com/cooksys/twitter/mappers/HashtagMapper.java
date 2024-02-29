package com.cooksys.twitter.mappers;

import java.util.List;

import com.cooksys.twitter.dtos.HashtagDto;
import com.cooksys.twitter.entities.Hashtag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HashtagMapper {
    HashtagDto entityToDto(Hashtag hashtag);
    List<HashtagDto> entitiesToDtos(List<Hashtag> hashtags);
}
