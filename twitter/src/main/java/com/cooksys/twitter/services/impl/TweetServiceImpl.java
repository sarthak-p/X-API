package com.cooksys.twitter.services.impl;

import com.cooksys.twitter.dtos.TweetResponseDto;
import com.cooksys.twitter.mappers.TweetMapper;
import com.cooksys.twitter.repositories.TweetRepository;
import com.cooksys.twitter.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {
    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;

    @Override
    public List<TweetResponseDto> getAllTweets() {
        return tweetMapper.entitiesToResponseDtos(tweetRepository.findAll());
    }
}
