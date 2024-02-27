package com.cooksys.twitter.services;

import com.cooksys.twitter.dtos.TweetResponseDto;

import java.util.List;

public interface TweetService {
    List<TweetResponseDto> getAllTweets();
}
