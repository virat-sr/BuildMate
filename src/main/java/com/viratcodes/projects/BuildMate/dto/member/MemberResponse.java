package com.viratcodes.projects.BuildMate.dto.member;

import com.viratcodes.projects.BuildMate.enums.ProjectRole;

import java.time.Instant;

public record MemberResponse(
        Long userId,
        String email,
        String name,
        ProjectRole projectRole,
        Instant InvitedAt
) {

}
