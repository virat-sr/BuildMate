package com.viratcodes.projects.BuildMate.service;

import com.viratcodes.projects.BuildMate.dto.auth.AuthResponse;
import com.viratcodes.projects.BuildMate.dto.auth.LoginRequest;
import com.viratcodes.projects.BuildMate.dto.auth.SignupRequest;

public interface AuthService {

    AuthResponse signup(SignupRequest request);

    AuthResponse login(LoginRequest request);

}
