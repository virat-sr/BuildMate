package com.viratcodes.projects.BuildMate.dto.member;

import com.viratcodes.projects.BuildMate.enums.ProjectRole;
import jakarta.validation.constraints.NotNull;

public record UpdateMemberRoleRequest(@NotNull ProjectRole projectRole) {

}
