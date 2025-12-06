package com.viratcodes.projects.BuildMate.dto.member;

import com.viratcodes.projects.BuildMate.ProjectRole;

import java.time.Instant;

public record MemberResponse(
        Long id,
        String email,
        String name,
        String avatarUrl,
        ProjectRole role,
        Instant InvitedAt
) {

}
