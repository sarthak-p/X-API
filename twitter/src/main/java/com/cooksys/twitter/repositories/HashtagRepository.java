package com.cooksys.twitter.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.twitter.entities.Hashtag;

@Repository
public class HashtagRepository extends JpaRepository<Hashtag, Long> {
    Optional<Hashtag> findById(Long hashtagId);

    Optional<Hashtag> findByLabel(String label);

}
