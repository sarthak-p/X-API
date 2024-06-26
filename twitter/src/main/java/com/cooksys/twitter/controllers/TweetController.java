package com.cooksys.twitter.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.twitter.dtos.ContextDto;
import com.cooksys.twitter.dtos.CredentialsDto;
import com.cooksys.twitter.dtos.HashtagDto;
import com.cooksys.twitter.dtos.TweetRequestDto;
import com.cooksys.twitter.dtos.TweetResponseDto;
import com.cooksys.twitter.dtos.UserResponseDto;
import com.cooksys.twitter.services.TweetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tweets")
@RequiredArgsConstructor
public class TweetController {
    private final TweetService tweetService;

    @GetMapping
    public List<TweetResponseDto> getAllTweets() {
        return tweetService.getAllTweets();
    }

    @GetMapping("/{id}")
    public TweetResponseDto getTweetById(@PathVariable Long id) {
        return tweetService.getTweetById(id);
    }
    
    @GetMapping("/{id}/tags")
    public List<HashtagDto> getTagsByTweetId(@PathVariable Long id) {
    	return tweetService.getTagsByTweetId(id);
    }
    
    @GetMapping("/{id}/likes")
    public List<UserResponseDto> getUserLikesByTweetId(@PathVariable Long id) {
    	return tweetService.getUserLikesByTweetId(id);
    }
    
    @GetMapping("/{id}/replies")
    public List<TweetResponseDto> getRepliesByTweetId(@PathVariable Long id) {
    	return tweetService.getRepliesByTweetId(id);
    }
    
    @GetMapping("/{id}/reposts")
    public List<TweetResponseDto> getRepostsByTweetId(@PathVariable Long id) {
    	return tweetService.getRepostsByTweetId(id);
    }
    
    @GetMapping("/{id}/mentions")
    public List<UserResponseDto> getMentionsByTweetId(@PathVariable Long id) {
    	return tweetService.getMentionsByTweetId(id);
    }
    
    @GetMapping("/{id}/context")
    public ContextDto getTweetContext(@PathVariable Long id) {
    	return tweetService.getTweetContext(id);
    }

    @PostMapping
    public TweetResponseDto createTweet(@RequestBody TweetRequestDto tweetRequestDto) {
        return tweetService.createTweet(tweetRequestDto);
    }
    
    @PostMapping("/{id}/like")
    public void createLike(@RequestBody CredentialsDto credentialsDto, @PathVariable Long id) {
    	tweetService.createLike(credentialsDto, id);
    }
    
    @PostMapping("/{id}/reply")
    public TweetResponseDto createReplyTweet(@RequestBody TweetRequestDto tweetRequestDto, @PathVariable Long id) {
    	return tweetService.createReplyTweet(tweetRequestDto, id);
    }
    
    @PostMapping("/{id}/repost")
    public TweetResponseDto createRepostTweet(@RequestBody CredentialsDto credentialsDto, @PathVariable Long id) {
    	return tweetService.createRepostTweet(credentialsDto, id);
    }
    
    @DeleteMapping("/{id}")
    public TweetResponseDto deleteTweet(@RequestBody CredentialsDto credentialsDto, @PathVariable Long id) {
    	return tweetService.deleteTweet(credentialsDto, id);
    }
}
