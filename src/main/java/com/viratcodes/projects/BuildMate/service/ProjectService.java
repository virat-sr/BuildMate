package com.viratcodes.projects.BuildMate.service;

import com.viratcodes.projects.BuildMate.dto.project.ProjectRequest;
import com.viratcodes.projects.BuildMate.dto.project.ProjectResponse;
import com.viratcodes.projects.BuildMate.dto.project.ProjectSummaryResponse;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface ProjectService {

    List<ProjectSummaryResponse> getUserProjects(Long userId);

    ProjectResponse getUserProjectsById(Long id, Long userId);

    ProjectResponse createProject(ProjectRequest request, Long userId);

    ProjectResponse updateProject(Long id, ProjectRequest request, Long userId);

    void softDelete(Long id, Long userId);

}
