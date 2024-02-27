package com.cooksys.twitter.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
public class Tweet {

    @Id
    @GeneratedValue
    private Long id;

    // @Column(nullable = false)
    // @ManyToOne(mappedBy = "tweet")
    // private User user;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp posted = Timestamp.valueOf(LocalDateTime.now());

    private String content;

    private boolean deleted;

    @ManyToOne
    private Tweet inReplyTo;

    @ManyToOne
    private Tweet repostOf;

//    @OneToMany(mappedBy = "tweet")
//    private List<User> user_likes;
//
//    @OneToMany(mappedBy = "tweet")
//    private List<User> user_mentions;
//
//    @OneToMany(mappedBy = "tweet")
//    private List<Hashtag> tweet_hashtags;

}
