package com.cooksys.twitter.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name="user_table")
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "username", column = @Column(name = "username")),
            @AttributeOverride( name = "password", column = @Column(name = "password"))
    })
    private Credentials credentials;

    @CreationTimestamp
    private Timestamp timeStamp;
    private boolean deleted;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "firstName", column = @Column(name = "first_name")),
            @AttributeOverride( name = "lastName", column = @Column(name = "last_name")),
            @AttributeOverride( name = "email", column = @Column(name = "email")),
            @AttributeOverride( name = "phone", column = @Column(name = "phone"))})
    private Profile profile;
}
