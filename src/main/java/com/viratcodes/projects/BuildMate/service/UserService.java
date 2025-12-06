package com.viratcodes.projects.BuildMate.service;

import com.viratcodes.projects.BuildMate.dto.auth.UserProfileResponse;

public interface UserService {

    UserProfileResponse getProfile(Long userId);

}
