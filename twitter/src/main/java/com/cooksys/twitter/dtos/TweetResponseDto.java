package com.cooksys.twitter.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class TweetResponseDto {

    private Long id;

    private Timestamp posted;

    private String content;

    private Long inReplyToId;

    private Long repostOfId;
}
