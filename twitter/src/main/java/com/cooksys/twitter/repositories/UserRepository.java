package com.cooksys.twitter.repositories;

import com.cooksys.twitter.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdAndDeletedFalse(Long id);

    List<User> findAllByDeletedFalse();

    Optional<User> findByCredentials_Username(String username);

}
