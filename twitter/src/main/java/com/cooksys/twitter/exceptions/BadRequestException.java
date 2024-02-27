package com.cooksys.twitter.exceptions;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = 7870470480238291984L;

    public BadRequestException(String message) {
        super(message);
    }
}
