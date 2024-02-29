package com.cooksys.twitter.services.impl;

import com.cooksys.twitter.dtos.TweetResponseDto;
import com.cooksys.twitter.exceptions.NotFoundException;
import com.cooksys.twitter.mappers.TweetMapper;
import com.cooksys.twitter.repositories.TweetRepository;
import com.cooksys.twitter.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {
    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;

    @Override
    public List<TweetResponseDto> getAllTweets() {
        List<TweetResponseDto> allTweets = tweetMapper.entitiesToResponseDtos(tweetRepository.findAllByDeletedFalse());
        if (allTweets.isEmpty()) {
            throw new NotFoundException("No tweets were found");
        }
        allTweets.sort(Comparator.comparing(TweetResponseDto::getPosted).reversed());
        return allTweets;
    }
}
