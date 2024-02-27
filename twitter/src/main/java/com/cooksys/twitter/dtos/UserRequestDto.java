package com.cooksys.twitter.dtos;

import com.cooksys.twitter.entities.Credentials;
import com.cooksys.twitter.entities.Profile;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequestDto {

    private Credentials credentials;
    private Profile profile;
}
