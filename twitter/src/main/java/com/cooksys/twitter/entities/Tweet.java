package com.cooksys.twitter.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Tweet {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, updatable = false)
        @CreationTimestamp
        private Timestamp posted = Timestamp.valueOf(LocalDateTime.now());

        private String content;

        private boolean deleted;

        @ManyToOne
        @JoinColumn
        private User author;

        @ManyToOne
        @JoinColumn
        private Tweet inReplyTo;

        @ManyToOne
        @JoinColumn
        private Tweet repostOf;

        @OneToMany(mappedBy = "inReplyTo")
        private List<Tweet> replies = new ArrayList<>();

        @OneToMany(mappedBy = "repostOf")
        private List<Tweet> reposts = new ArrayList<>();

        @Column(nullable = false, updatable = false)
        @CreationTimestamp
        private LocalDateTime createdTimestamp;

        @ManyToMany
        @JoinTable(name = "tweet_user_likes", joinColumns = @JoinColumn(name = "tweet_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
        private List<User> user_likes = new ArrayList<>();

        @ManyToMany(cascade = CascadeType.PERSIST)
        @JoinTable(name = "tweet_user_mentions", joinColumns = @JoinColumn(name = "tweet_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
        private List<User> mentionedUsers = new ArrayList<>();

        @ManyToMany
        @JoinTable(name = "tweet_hashtags", joinColumns = @JoinColumn(name = "tweet_id"), inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
        private List<Hashtag> hashtags = new ArrayList<>();

}
