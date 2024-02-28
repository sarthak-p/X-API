package com.cooksys.twitter.services;

public interface ValidateService {
    boolean hashtagExists(String label);
    boolean usernameExists(String username);
}
