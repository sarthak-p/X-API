package com.cooksys.twitter.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.twitter.dtos.HashtagDto;
import com.cooksys.twitter.dtos.TweetResponseDto;
import com.cooksys.twitter.entities.Hashtag;
import com.cooksys.twitter.exceptions.BadRequestException;
import com.cooksys.twitter.mappers.HashtagMapper;
import com.cooksys.twitter.mappers.TweetMapper;
import com.cooksys.twitter.repositories.HashtagRepository;
import com.cooksys.twitter.repositories.TweetRepository;
import com.cooksys.twitter.services.HashtagService;
import com.cooksys.twitter.services.ValidateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
    private final HashtagRepository hashtagRepository;
    private final TweetRepository tweetRepository;
    private final HashtagMapper hashtagMapper;
    private final TweetMapper tweetMapper;
    private final ValidateService validateService;

    @Override
    public List<HashtagDto> getAllHashtags() {
        List<Hashtag> hashtags = hashtagRepository.findAll();
        return hashtagMapper.entitiesToDtos(hashtags);
    }

	@Override
	public List<TweetResponseDto> getTweetsByHashtagLabel(String label) {
		// check if hashtag exists
		if (!validateService.hashtagExists(label)) {
			throw new BadRequestException("hashtag label doesn't exist");
		}
		return tweetMapper.entitiesToResponseDtos(tweetRepository.findAllByDeletedFalseAndHashtags_Label(label)); 
	}

}
