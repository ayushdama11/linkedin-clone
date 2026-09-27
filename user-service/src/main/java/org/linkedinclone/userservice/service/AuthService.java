package org.linkedinclone.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.linkedinclone.userservice.dto.SignupRequestDto;
import org.linkedinclone.userservice.dto.UserDto;
import org.linkedinclone.userservice.entity.User;
import org.linkedinclone.userservice.exception.BadRequestException;
import org.linkedinclone.userservice.repository.UserRepository;
import org.linkedinclone.userservice.utils.Bcrypt;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserDto signUp(SignupRequestDto signupRequestDto) {
        log.info("Signup a user with email: {}", signupRequestDto.getEmail());

        boolean userExists = userRepository.existsByEmail(signupRequestDto.getEmail());
        if(userExists) {
            throw new BadRequestException("User already exists");
        }

        User user = modelMapper.map(signupRequestDto, User.class);
        user.setPassword(Bcrypt.hash(signupRequestDto.getPassword()));

        user = userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

}
