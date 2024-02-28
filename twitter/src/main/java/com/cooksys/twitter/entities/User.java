package com.cooksys.twitter.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
            @AttributeOverride( name = "email", column = @Column(name = "email", nullable = false)),
            @AttributeOverride( name = "phone", column = @Column(name = "phone"))})
    private Profile profile;

    @ManyToMany
    @JoinTable(
            name = "user_following",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private List<User> following = new ArrayList<>();

    @ManyToMany(mappedBy = "following")
    private List<User> followers = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "tweet_mentioned_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tweet_id")
    )
    private List<Tweet> mentionedInTweets = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private List<Tweet> tweets = new ArrayList<>();

    @ManyToMany(mappedBy = "user_likes")
    private List<Tweet> likedTweets = new ArrayList<>();
}
