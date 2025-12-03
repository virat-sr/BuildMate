package com.viratcodes.projects.BuildMate.entity;

import java.time.Instant;

public class ProjectFile {
    Long id;
    Project project;
    String path;
    String minioObject;
    Instant createdAt;
    Instant updatedAt;
    User createdBy;
    User updatedBy;
}
