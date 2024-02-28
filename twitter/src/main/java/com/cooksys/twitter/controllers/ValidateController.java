package com.cooksys.twitter.controllers;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.twitter.services.ValidateService;

@RestController
@RequestMapping("/validate")
@AllArgsConstructor
public class ValidateController {

    private final ValidateService validateService;

    // Checks whether a given hashtag exists.
    @GetMapping("/tag/exists/{label}")
    public Boolean hashtagExists(@PathVariable String label) {
        return validateService.hashtagExists(label);
    }

    @GetMapping("/username/available@{username}")
    public boolean usernameExists(@PathVariable String username) {
        return validateService.usernameExists(username);
    }

    // Check whether a given username is available
    @GetMapping("/username/available/{username}")
    public Boolean usernameAvailable(@PathVariable String username) {
        return validateService.usernameAvailable(username);
    }
}
