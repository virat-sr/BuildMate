package com.viratcodes.projects.BuildMate.controller;

import com.viratcodes.projects.BuildMate.dto.auth.AuthResponse;
import com.viratcodes.projects.BuildMate.dto.auth.LoginRequest;
import com.viratcodes.projects.BuildMate.dto.auth.SignupRequest;
import com.viratcodes.projects.BuildMate.dto.auth.UserProfileResponse;
import com.viratcodes.projects.BuildMate.service.AuthService;
import com.viratcodes.projects.BuildMate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(SignupRequest request) {
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    public ResponseEntity<UserProfileResponse> getProfile(){
        Long userId = 1L; //Spring security will help us later
        return ResponseEntity.ok(userService.getProfile(userId));
    }



}
