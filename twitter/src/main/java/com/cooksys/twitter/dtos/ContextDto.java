package com.cooksys.twitter.dtos;

import com.cooksys.twitter.entities.Tweet;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContextDto {
    private Tweet target;
    private Tweet[] before;
    private Tweet[] after;
}
