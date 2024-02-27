package com.cooksys.twitter.dtos;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProfileDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
