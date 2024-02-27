package com.cooksys.twitter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cooksys.twitter.entities.Tweet;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
}
