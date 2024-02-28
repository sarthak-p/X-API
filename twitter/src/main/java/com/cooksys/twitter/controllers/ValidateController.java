package com.cooksys.twitter.controllers;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.twitter.services.ValidateService;

@RestController("/validate")
@AllArgsConstructor
public class ValidateController {
    private final ValidateService validateService;

    // check whether a given hashtag exits
    @GetMapping("/tag/exists/{label}")
    public Boolean hashtagExists(@PathVariable String label) {
        return validateService.hashtagExists(label);
    }

    // check whether a given username exists
    @GetMapping("/username/exists/@{username}")
    public Boolean usernameExists(@PathVariable String username) {
        return validateService.usernameExists(username);
    }

    // check whether a given username is available
}
