package com.cooksys.twitter.exceptions;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class NotAuthorizedException extends RuntimeException {
    private static final long serialVersionUID = 6616542207890424201L;

    public NotAuthorizedException(String message) {
        super(message);
    }
}
