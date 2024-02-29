package com.cooksys.twitter.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.twitter.dtos.HashtagDto;
import com.cooksys.twitter.entities.Hashtag;
import com.cooksys.twitter.mappers.HashtagMapper;
import com.cooksys.twitter.repositories.HashtagRepository;
import com.cooksys.twitter.services.HashtagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
    private final HashtagRepository hashtagRepository;
    private final HashtagMapper hashtagMapper;

    @Override
    public List<HashtagDto> getAllHashtags() {
        List<Hashtag> hashtags = hashtagRepository.findAll();
        System.out.println("Fetched hashtags: " + hashtags); // Debugging purpose
        return hashtagMapper.entitiesToDtos(hashtags);
    }

}
