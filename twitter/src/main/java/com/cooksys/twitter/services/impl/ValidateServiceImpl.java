package com.cooksys.twitter.services.impl;

import com.cooksys.twitter.entities.Hashtag;
import com.cooksys.twitter.entities.User;
import com.cooksys.twitter.repositories.HashtagRepository;
import com.cooksys.twitter.repositories.UserRepository;
import com.cooksys.twitter.services.ValidateService;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {
    private final HashtagRepository hashtagRepository;
    // private final UserRepository userRepository;

    @Override
    public boolean hashtagExists(String label) {
        Optional<Hashtag> hashtagOptional = hashtagRepository.findByLabel(label);
        return hashtagOptional.isPresent();
    }

    @Override
    public boolean usernameExists(String username) {
        return true;
    }
}
