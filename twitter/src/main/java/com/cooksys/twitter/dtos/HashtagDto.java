package com.cooksys.twitter.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class HashtagDto {
    private String label;
    private Timestamp firstUsed;
    private Timestamp lastUsed;
}
