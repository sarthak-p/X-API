package com.cooksys.twitter.exceptions;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = 8864329528979368162L;

    public NotFoundException(String message) {
        super(message);
    }
}
