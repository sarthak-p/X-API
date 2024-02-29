package com.cooksys.twitter.services.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.cooksys.twitter.dtos.TweetRequestDto;
import com.cooksys.twitter.dtos.TweetResponseDto;
import com.cooksys.twitter.entities.Hashtag;
import com.cooksys.twitter.entities.Tweet;
import com.cooksys.twitter.entities.User;
import com.cooksys.twitter.exceptions.NotFoundException;
import com.cooksys.twitter.mappers.TweetMapper;
import com.cooksys.twitter.repositories.TweetRepository;
import com.cooksys.twitter.repositories.UserRepository;
import com.cooksys.twitter.services.TweetService;
import com.cooksys.twitter.services.ValidateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {
	private final TweetRepository tweetRepository;
	private final UserRepository userRepository;
	private final TweetMapper tweetMapper;
	private ValidateService validateService;
	
	// processes content for hashtags
	private List<Hashtag> processTweetForTags(String tweetContent) {
		String hashtagPattern = "#+[a-zA-Z0-9(_)]{1,}";
		Pattern pattern = Pattern.compile(hashtagPattern);
		Matcher matcher = pattern.matcher(tweetContent);
		List<Hashtag> tagsInContent = new ArrayList<>();
		while (matcher.find()) {
			Hashtag hashtagInTweet = new Hashtag();
			hashtagInTweet.setLabel(matcher.group());
			tagsInContent.add(hashtagInTweet);
		}
		return tagsInContent;
	}
	
	// processes content for user mentions
	private List<User> processTweetForUsers(String tweetContent) {
		String userPattern = "@+[a-zA-Z0-9(_)]{1,}";
		Pattern pattern = Pattern.compile(userPattern);
		Matcher matcher = pattern.matcher(tweetContent);
		List<User> usersInContent = new ArrayList<>();
		while (matcher.find()) {
			User userInContent = new User();
			userInContent.getCredentials().setUsername(matcher.group());
			usersInContent.add(userInContent);
		}
		return usersInContent;
	}

	@Override
	public List<TweetResponseDto> getAllTweets() {
		List<TweetResponseDto> allTweets = tweetMapper.entitiesToResponseDtos(tweetRepository.findAllByDeletedFalse());
		if (allTweets.isEmpty()) {
			throw new NotFoundException("No tweets were found");
		}
		allTweets.sort(Comparator.comparing(TweetResponseDto::getPosted).reversed());
		return allTweets;
	}

	@Override
	public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {
		// check if given credentials match user in the db
		if (!validateService.usernameExists(tweetRequestDto.getCredentials().getUsername())) {
			throw new NotFoundException("User not found");
		}

		// Create new tweet
		Tweet createdTweet = new Tweet();
		createdTweet.setDeleted(false);
		createdTweet.setContent(tweetRequestDto.getContent());
		createdTweet.setAuthor(
				userRepository.findByCredentials_Username(tweetRequestDto.getCredentials().getUsername()).get());

		// process content for @{username} mentions and #{hashtag} tags
		createdTweet.setHashtags(processTweetForTags(createdTweet.getContent()));
		createdTweet.setMentionedUsers(processTweetForUsers(createdTweet.getContent()));

		return tweetMapper.entityToResponseDto(tweetRepository.saveAndFlush(createdTweet));
	}
}
