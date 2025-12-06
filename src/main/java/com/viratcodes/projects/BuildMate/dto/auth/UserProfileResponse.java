package com.viratcodes.projects.BuildMate.dto.auth;

public record UserProfileResponse(
        Long id,
        String email,
        String name,
        String avatarUrl) {

}
