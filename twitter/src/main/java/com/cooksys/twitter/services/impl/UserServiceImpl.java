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

import javax.swing.text.html.Option;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

    private void validateUser(UserRequestDto userRequestDto){
        CredentialsDto credentialsDtoToCreate = userRequestDto.getCredentials();
        ProfileDto profileDtoToCreate = userRequestDto.getProfile();
        if (credentialsDtoToCreate == null || profileDtoToCreate == null){
            throw new BadRequestException("Profile or Credentials cannot be null");
        } else if (validateService.usernameExists(credentialsDtoToCreate.getUsername())) {
            throw new BadRequestException("Username already taken");
        } else if (profileDtoToCreate.getEmail() == null){
            throw new BadRequestException("Email cannot be null");
        } else if(credentialsDtoToCreate.getUsername() == null || credentialsDtoToCreate.getPassword() == null){
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
                    user.getCredentials().getPassword().equals(userRequestDto.getCredentials().getPassword())) {
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
        if(!validateService.usernameExists(username) || optionalUser.get().isDeleted()){
            throw new NotFoundException("Username not found");
        }
        System.out.println(optionalUser.get().getJoined());
        return userMapper.entityToResponseDto(optionalUser.get());
    }

}
