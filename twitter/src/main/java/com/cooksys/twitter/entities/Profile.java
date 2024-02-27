package com.cooksys.twitter.entities;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
@Embeddable
@Data
@NoArgsConstructor
public class Profile {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
