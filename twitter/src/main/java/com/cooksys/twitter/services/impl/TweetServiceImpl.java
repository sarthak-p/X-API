package com.cooksys.twitter.services.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.cooksys.twitter.dtos.CredentialsDto;
import com.cooksys.twitter.dtos.HashtagDto;
import com.cooksys.twitter.dtos.TweetRequestDto;
import com.cooksys.twitter.dtos.TweetResponseDto;
import com.cooksys.twitter.dtos.UserResponseDto;
import com.cooksys.twitter.entities.Hashtag;
import com.cooksys.twitter.entities.Tweet;
import com.cooksys.twitter.entities.User;
import com.cooksys.twitter.exceptions.BadRequestException;
import com.cooksys.twitter.exceptions.NotFoundException;
import com.cooksys.twitter.mappers.HashtagMapper;
import com.cooksys.twitter.mappers.TweetMapper;
import com.cooksys.twitter.mappers.UserMapper;
import com.cooksys.twitter.repositories.HashtagRepository;
import com.cooksys.twitter.repositories.TweetRepository;
import com.cooksys.twitter.repositories.UserRepository;
import com.cooksys.twitter.services.TweetService;
import com.cooksys.twitter.services.ValidateService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TweetServiceImpl implements TweetService {
	private final TweetRepository tweetRepository;
	private final UserRepository userRepository;
	private final HashtagRepository hashtagRepository;
	private final TweetMapper tweetMapper;
	private final HashtagMapper hashtagMapper;
	private final UserMapper userMapper;
	private ValidateService validateService;

	// processes content for hashtags
	private List<Hashtag> processTweetForTags(String tweetContent) {
		String hashtagPattern = "#+[a-zA-Z0-9(_)]{1,}";
		Pattern pattern = Pattern.compile(hashtagPattern);
		Matcher matcher = pattern.matcher(tweetContent);
		List<Hashtag> tagsInContent = new ArrayList<>();
		while (matcher.find()) {
			Hashtag hashtagInTweet = new Hashtag();
			hashtagInTweet.setLabel(matcher.group().substring(1));
			tagsInContent.add(hashtagInTweet);
			hashtagRepository.saveAndFlush(hashtagInTweet);
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
			userInContent = userRepository.findByCredentials_Username(matcher.group().substring(1)).get();
			usersInContent.add(userInContent);
		}
		return usersInContent;
	}

	private void validateTweet(TweetRequestDto tweetRequestDto) {
		CredentialsDto credentialsDtoToCreate = tweetRequestDto.getCredentials();
		String contentToCreate = tweetRequestDto.getContent();
		if (credentialsDtoToCreate == null || contentToCreate == null) {
			throw new BadRequestException("Content or Credentials cannot be null");
		} else if (!validateService.usernameExists(credentialsDtoToCreate.getUsername())) {
			throw new BadRequestException("User doesn't exist in the database");
		} else if (credentialsDtoToCreate.getUsername() == null || credentialsDtoToCreate.getPassword() == null) {
			throw new BadRequestException("Username or password cannot be null");
		}
	}

	private Tweet getTweet(Long id) {
		Optional<Tweet> tweetById = tweetRepository.findByIdAndDeletedFalse(id);
		if (!tweetById.isPresent()) {
			throw new BadRequestException("No such tweet exists");
		} else if (tweetById.get().isDeleted()) {
			throw new BadRequestException("Tweet is deleted");
		}
		return tweetById.get();
	}

	// GET
	// ----------

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
	public TweetResponseDto getTweetById(Long id) {
		return tweetMapper.entityToResponseDto(getTweet(id));
	}

	@Override
	public List<HashtagDto> getTagsByTweetId(Long id) {
		// check if the tweet exists and if it's deleted
		Tweet tweet = getTweet(id);
		return hashtagMapper.entitiesToDtos(tweet.getHashtags());
	}

	@Override
	public List<UserResponseDto> getUserLikesByTweetId(Long id) {
		// check if the tweet exists and if it's deleted
		Tweet tweet = getTweet(id);
		// only return non-deleted users
		List<User> nonDeletedUsers = new ArrayList<>();
		for (User u : tweet.getUser_likes()) {
			if (!u.isDeleted()) {
				nonDeletedUsers.add(u);
			}
		}
		return userMapper.entitiesToResponseDtos(nonDeletedUsers);
	}

	// POST
	// ----------
	@Override
	public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {
		validateTweet(tweetRequestDto);

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

	@Override
	public void createLike(CredentialsDto credentialsDto, Long id) {
		// checks if tweet exists and if it's deleted
		Tweet tweetToLike = getTweet(id);
		// check if credentials don't match author
		if (userRepository.findByCredentials_Username(credentialsDto.getUsername()).get() == null) {
			throw new BadRequestException("User doesn't exist in the database");
		}
		// create like relationship between user and tweet
		User userToLikeTweet = userRepository.findByCredentials_Username(credentialsDto.getUsername()).get();
		userToLikeTweet.getLikedTweets().add(tweetToLike);
		tweetToLike.getUser_likes().add(userToLikeTweet);

		// save to database
		userRepository.saveAndFlush(userToLikeTweet);
		tweetRepository.saveAndFlush(tweetToLike);

	}

	@Override
	public TweetResponseDto createReplyTweet(TweetRequestDto tweetRequestDto, Long id) {
		// if tweet is deleted or doesn't exist or credentials don't match user in db
		Tweet tweetToReplyTo = getTweet(id);
		if (userRepository.findByCredentials_Username(tweetRequestDto.getCredentials().getUsername()).get() == null) {
			throw new BadRequestException("User doesn't exist in the database");
		}
		// create reply tweet
		Tweet replyTweet = new Tweet();
		replyTweet.setDeleted(false);
		replyTweet.setContent(tweetRequestDto.getContent());
		replyTweet.setAuthor(
				userRepository.findByCredentials_Username(tweetRequestDto.getCredentials().getUsername()).get());
		// process content for @{username} mentions and #{hashtag} tags
		replyTweet.setHashtags(processTweetForTags(replyTweet.getContent()));
		replyTweet.setMentionedUsers(processTweetForUsers(replyTweet.getContent()));
		// add reply to relationship
		replyTweet.setInReplyTo(tweetToReplyTo);
		tweetToReplyTo.getReplies().add(replyTweet);
		// save to database
		tweetRepository.saveAndFlush(replyTweet);
		tweetRepository.saveAndFlush(tweetToReplyTo);
		return tweetMapper.entityToResponseDto(replyTweet);
	}

	@Override
	public TweetResponseDto createRepostTweet(CredentialsDto credentialsDto, Long id) {
		// if tweet is deleted or doesn't exist or credentials don't match user in db
		Tweet givenTweet = getTweet(id);
		if (userRepository.findByCredentials_Username(credentialsDto.getUsername()).get() == null) {
			throw new BadRequestException("User doesn't exist in the database");
		}
		// create reply tweet
		Tweet repostOfGivenTweet = new Tweet();
		repostOfGivenTweet.setDeleted(false);
		repostOfGivenTweet.setAuthor(userRepository.findByCredentials_Username(credentialsDto.getUsername()).get());
		// add reply to relationship
		repostOfGivenTweet.setRepostOf(givenTweet);
		givenTweet.getReposts().add(repostOfGivenTweet);
		// save to database
		tweetRepository.saveAndFlush(repostOfGivenTweet);
		tweetRepository.saveAndFlush(givenTweet);
		return tweetMapper.entityToResponseDto(repostOfGivenTweet);
	}

	// DELETE
	// --------
	@Override
	public TweetResponseDto deleteTweet(CredentialsDto credentialsDto, Long id) {
		// check if no tweet exists
		Tweet tweetToDelete = getTweet(id);
		// check if credentials don't match author
		if (!tweetToDelete.getAuthor()
				.equals(userRepository.findByCredentials_Username(credentialsDto.getUsername()).get())) {
			throw new BadRequestException("Tweet author doesn't match credentials");
		}
		// delete tweet
		tweetToDelete.setDeleted(true);
		return tweetMapper.entityToResponseDto(tweetRepository.saveAndFlush(tweetToDelete));
	}

}
