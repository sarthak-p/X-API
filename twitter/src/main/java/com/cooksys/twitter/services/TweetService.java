package com.cooksys.twitter.services;

import java.util.List;

import com.cooksys.twitter.dtos.CredentialsDto;
import com.cooksys.twitter.dtos.HashtagDto;
import com.cooksys.twitter.dtos.TweetRequestDto;
import com.cooksys.twitter.dtos.TweetResponseDto;
import com.cooksys.twitter.dtos.UserResponseDto;

public interface TweetService {
    List<TweetResponseDto> getAllTweets();

    TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

    TweetResponseDto getTweetById(Long id);

	TweetResponseDto deleteTweet(CredentialsDto credentialsDto, Long id);

	void createLike(CredentialsDto credentialsDto, Long id);

	TweetResponseDto createReplyTweet(TweetRequestDto tweetRequestDto, Long id);

	TweetResponseDto createRepostTweet(CredentialsDto credentialsDto, Long id);

	List<HashtagDto> getTagsByTweetId(Long id);

	List<UserResponseDto> getUserLikesByTweetId(Long id);

	List<TweetResponseDto> getRepliesByTweetId(Long id);

	List<TweetResponseDto> getRepostsByTweetId(Long id);
}
