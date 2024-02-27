package com.cooksys.twitter.services;

import java.util.List;

import com.cooksys.twitter.dtos.HashtagDto;

public interface HashtagService {
    public List<HashtagDto> getAllHashtags();
}
