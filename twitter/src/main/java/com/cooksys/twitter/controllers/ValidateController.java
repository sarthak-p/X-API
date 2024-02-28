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

    // check whether a given hashtag exits
    @GetMapping("/tag/exists/{label}")
    public ResponseEntity<Boolean> hashtagExists(@PathVariable String label) {
        boolean exists = validateService.hashtagExists(label);
        return ResponseEntity.ok(exists);
    }

    // check whether a given username exists
    @GetMapping("/username/exists/@{username}")
    public ResponseEntity<Boolean> usernameExists(@PathVariable String username) {
        boolean exists = validateService.usernameExists(username);
        return ResponseEntity.ok(exists);
    }

    // check whether a given username is
    @GetMapping("/username/available/{username}")
    public ResponseEntity<Boolean> usernameAvailable(@PathVariable String username) {
        boolean available = validateService.usernameAvailable(username);
        return ResponseEntity.ok(available);
    }
}
