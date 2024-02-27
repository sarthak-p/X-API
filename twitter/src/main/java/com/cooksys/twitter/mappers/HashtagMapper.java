package com.cooksys.twitter.mappers;

import java.util.List;

import com.cooksys.twitter.dtos.HashtagDto;
import com.cooksys.twitter.entities.Hashtag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { HashtagMapper.class })
public interface HashtagMapper {
    HashtagDto entityToDto(Hashtag hashtag);

    List<HashtagDto> entitiesToDtos(List<Hashtag> hashtags);

    Hashtag dtoToEntity(HashtagDto dto);
}
