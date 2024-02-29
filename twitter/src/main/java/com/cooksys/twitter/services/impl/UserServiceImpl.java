package com.cooksys.twitter.services.impl;

import com.cooksys.twitter.dtos.CredentialsDto;
import com.cooksys.twitter.dtos.ProfileDto;
import com.cooksys.twitter.dtos.UserRequestDto;
import com.cooksys.twitter.dtos.UserResponseDto;
import com.cooksys.twitter.entities.Profile;
import com.cooksys.twitter.entities.User;
import com.cooksys.twitter.exceptions.BadRequestException;
import com.cooksys.twitter.exceptions.NotFoundException;
import com.cooksys.twitter.mappers.CredentialsMapper;
import com.cooksys.twitter.mappers.ProfileMapper;
import com.cooksys.twitter.mappers.UserMapper;
import com.cooksys.twitter.repositories.UserRepository;
import com.cooksys.twitter.services.UserService;
import com.cooksys.twitter.services.ValidateService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.swing.text.html.Option;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private ValidateService validateService;
    private ProfileMapper profileMapper;
    private CredentialsMapper credentialsMapper;

    private void validateUser(UserRequestDto userRequestDto) {
        CredentialsDto credentialsDtoToCreate = userRequestDto.getCredentials();
        ProfileDto profileDtoToCreate = userRequestDto.getProfile();
        if (credentialsDtoToCreate == null || profileDtoToCreate == null) {
            throw new BadRequestException("Profile or Credentials cannot be null");
        } else if (validateService.usernameExists(credentialsDtoToCreate.getUsername())) {
            for (User user : userRepository.findAll()) {
                if (user.getCredentials().getUsername().equals(userRequestDto.getCredentials().getUsername()) &&
                        user.getCredentials().getPassword().equals(userRequestDto.getCredentials().getPassword()) && user.isDeleted()) {
                    return;
                }
            }
            throw new BadRequestException("Username already taken");
        } else if (profileDtoToCreate.getEmail() == null) {
            throw new BadRequestException("Email cannot be null");
        } else if (credentialsDtoToCreate.getUsername() == null || credentialsDtoToCreate.getPassword() == null) {
            throw new BadRequestException("Username or password cannot be null");
        }
    }

    @Override
    public User getUser(Long id) {
        Optional<User> optionalUser = userRepository.findByIdAndDeletedFalse(id);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User not found with id " + id);
        }
        return optionalUser.get();
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userMapper.entitiesToResponseDtos(userRepository.findAllByDeletedFalse());
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {

        validateUser(userRequestDto);

        for (User user : userRepository.findAll()) {
            if (user.getCredentials().getUsername().equals(userRequestDto.getCredentials().getUsername()) &&
                    user.getCredentials().getPassword().equals(userRequestDto.getCredentials().getPassword())
                    && user.isDeleted()) {
                user.setDeleted(false);
                return userMapper.entityToResponseDto(userRepository.saveAndFlush(user));
            }
        }

        User userToCreate = new User();
        userToCreate.setCredentials(credentialsMapper.dtoToEntity(userRequestDto.getCredentials()));
        userToCreate.setProfile(profileMapper.dtoToEntity(userRequestDto.getProfile()));
        userRepository.saveAndFlush(userToCreate);

        return userMapper.entityToResponseDto(userToCreate);
    }

    @Override
    public UserResponseDto getUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByCredentials_Username(username);
        if (!validateService.usernameExists(username) || optionalUser.get().isDeleted()) {
            throw new NotFoundException("Username not found");
        }
        System.out.println(optionalUser.get().getJoined());
        return userMapper.entityToResponseDto(optionalUser.get());
    }

    @Override
    public UserResponseDto deleteUser(String username, CredentialsDto credentialsDto) {
        if (!validateService.usernameExists(username) || !credentialsDto.getUsername().equals(username)) {
            throw new BadRequestException("Username does not exist");
        }
        Optional<User> optionalUser = userRepository.findByCredentials_Username(username);
        User userToDelete = optionalUser.get();
        userToDelete.setDeleted(true);
        return userMapper.entityToResponseDto(userRepository.saveAndFlush(userToDelete));
    }

    @Override
    public UserResponseDto updateUser(String username, UserRequestDto userRequestDto) {
        Optional<User> userToUpdate = userRepository.findByCredentials_Username(username);
        if (!validateService.usernameExists(username) || userToUpdate.isPresent() && userToUpdate.get().isDeleted()) {
            throw new BadRequestException("This user does not exist");
        } else if (userRequestDto.getCredentials() == null || userRequestDto.getProfile() == null) {
            throw new BadRequestException("Credentials should not be null");
        } else if (!userRequestDto.getCredentials().equals(credentialsMapper.entityToDto(userToUpdate.get().getCredentials()))) {
            throw new BadRequestException("Invalid information");
        }
        if (ObjectUtils.isEmpty(userRequestDto.getProfile())) {
            userToUpdate.get().setCredentials(credentialsMapper.dtoToEntity(userRequestDto.getCredentials()));
        }
        return userMapper.entityToResponseDto(userRepository.saveAndFlush(userToUpdate.get()));
    }

    @Override
    public void followUser(String username, CredentialsDto credentialsDto) {
        if (credentialsDto == null) {
            throw new BadRequestException("No credentials provided");
        } else if (credentialsDto.getUsername() == null || credentialsDto.getPassword() == null) {
            throw new BadRequestException("Credentials cannot be null");
        }
        Optional<User> userToFollow = userRepository.findByCredentials_Username(username);
        Optional<User> follower = userRepository.findByCredentials_Username(credentialsDto.getUsername());
        if (userToFollow.get().getFollowers().contains(follower.get())) {
            throw new BadRequestException("Following relationship is already active");
        } else if (!userRepository.findAllByDeletedFalse().contains(userToFollow.get())) {
            throw new BadRequestException("Followable user does not exist");
        } else if (!userRepository.findAllByDeletedFalse().contains(follower.get())) {
            throw new BadRequestException("Credentials not found");
        } else {
            userToFollow.get().getFollowers().add(follower.get());
            follower.get().getFollowing().add(userToFollow.get());
            userRepository.saveAndFlush(userToFollow.get());
            userRepository.saveAndFlush(follower.get());
        }
    }

    private User followValidation(String username) {
        Optional<User> validateFollower = userRepository.findByCredentials_Username(username);
        if (!validateService.usernameExists(username)) {
            throw new BadRequestException("user does not exist");
        } else if (validateFollower.get().isDeleted()) {
            throw new BadRequestException("User does not exist");
        }
        return validateFollower.get();
    }

    @Override
    public List<UserResponseDto> getFollowers(String username) {
        User userFollowers = followValidation(username);
        List<UserResponseDto> followerList = new ArrayList<>();
        for (User user : userFollowers.getFollowers()) {
            if (!user.isDeleted()) {
                followerList.add(userMapper.entityToResponseDto(user));
            }
        }

        return followerList;
    }

    @Override
    public List<UserResponseDto> getFollowing(String username) {
        User userFollowing = followValidation(username);
        List<UserResponseDto> followingList = new ArrayList<>();
        for (User user : userFollowing.getFollowing()) {
            if (!user.isDeleted()) {
                followingList.add(userMapper.entityToResponseDto(user));
            }
        }
        return followingList;
    }

    @Override
    public void unfollowUser(String username, CredentialsDto credentialsDto) {

        User userToUnfollow;
        User userUnfollowing;
        if (credentialsDto == null || credentialsDto.getPassword() == null || credentialsDto.getUsername() == null) {
            throw new BadRequestException("Credentials cannot be null");
        } else if (!validateService.usernameExists(username)) {
            throw new BadRequestException("User does not exist");
        }

        userToUnfollow = userRepository.findByCredentials_Username(username).get();
        userUnfollowing = userRepository.findByCredentials_Username(credentialsDto.getUsername()).get();
        if (!userToUnfollow.getFollowers().contains(userUnfollowing)) {
            throw new BadRequestException("No existing relationship");
        } else if (!userRepository.findAllByDeletedFalse().contains(userUnfollowing)) {
            throw new BadRequestException("Credentials not found");
        }
        userToUnfollow.getFollowers().remove(userUnfollowing);
        userUnfollowing.getFollowing().remove(userToUnfollow);
        userRepository.saveAndFlush(userToUnfollow);
        userRepository.saveAndFlush(userUnfollowing);
    }

}
