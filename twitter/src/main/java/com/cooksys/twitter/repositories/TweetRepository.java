package com.cooksys.twitter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cooksys.twitter.entities.Tweet;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
    Optional<Tweet> findById(Long id);

    List<Tweet> findAllByDeletedFalse();
}
