package com.cooksys.twitter.repositories;

import java.util.List;
import java.util.Optional;

import com.cooksys.twitter.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.twitter.dtos.TweetResponseDto;
import com.cooksys.twitter.entities.Tweet;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
    Optional<Tweet> findById(Long id);

    List<Tweet> findAllByDeletedFalse();

    Optional<Tweet> findByIdAndDeletedFalse(Long id);
    List<Tweet> findAllByDeletedFalseAndAuthor(User user);
}
