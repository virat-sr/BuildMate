package com.viratcodes.projects.BuildMate.entity;

import com.viratcodes.projects.BuildMate.ProjectRole;

import java.time.Instant;

public class ProjectMember {
    ProjectMemberId id;
    Project project;
    User user;
    ProjectRole projectRole;
    Instant invitedAt;
    Instant acceptedAt;
}

