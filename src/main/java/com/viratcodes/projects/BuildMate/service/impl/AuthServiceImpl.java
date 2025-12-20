package com.viratcodes.projects.BuildMate.service.impl;

import com.viratcodes.projects.BuildMate.dto.auth.AuthResponse;
import com.viratcodes.projects.BuildMate.dto.auth.LoginRequest;
import com.viratcodes.projects.BuildMate.dto.auth.SignupRequest;
import com.viratcodes.projects.BuildMate.entity.User;
import com.viratcodes.projects.BuildMate.error.BadRequestException;
import com.viratcodes.projects.BuildMate.mapper.UserMapper;
import com.viratcodes.projects.BuildMate.repository.UserRepository;
import com.viratcodes.projects.BuildMate.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;


    @Override
    public AuthResponse signup(SignupRequest request) {
        userRepository.findByUsername(request.username()).ifPresent(user -> {
            throw new BadRequestException("User Already Exists with Username: "+ request.username());
        });

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        user = userRepository.save(user);

        return new AuthResponse("dummy", userMapper.toUserProfileResponse(user));
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        return null;
    }

}
