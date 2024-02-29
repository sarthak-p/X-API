package com.cooksys.twitter.services;

import java.util.List;

import com.cooksys.twitter.dtos.CredentialsDto;
import com.cooksys.twitter.dtos.TweetRequestDto;
import com.cooksys.twitter.dtos.TweetResponseDto;
import com.cooksys.twitter.entities.Tweet;

public interface TweetService {
    List<TweetResponseDto> getAllTweets();

    TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

    TweetResponseDto getTweetById(Long id);

	TweetResponseDto deleteTweet(CredentialsDto credentialsDto, Long id);
}
