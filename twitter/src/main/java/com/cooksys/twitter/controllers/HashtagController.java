package com.cooksys.twitter.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.twitter.dtos.HashtagDto;
import com.cooksys.twitter.services.HashtagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class HashtagController {
    private final HashtagService hashtagService;

    @GetMapping
    public List<HashtagDto> getAllHashtags() {
        return hashtagService.getAllHashtags();
    }

    @GetMapping("/randomTag")
    public 
}
