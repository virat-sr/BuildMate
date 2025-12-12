package com.viratcodes.projects.BuildMate.dto.member;

import com.viratcodes.projects.BuildMate.enums.ProjectRole;

public record InviteMemberRequest(
        String email,
        ProjectRole role
) {

}
