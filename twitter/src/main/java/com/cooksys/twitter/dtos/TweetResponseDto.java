package com.cooksys.twitter.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class TweetResponseDto {

    private Long id;
    
    private UserResponseDto author;

    private Timestamp posted;

    private String content;

    private TweetResponseDto inReplyToId;

    private TweetResponseDto repostOfId;
}
