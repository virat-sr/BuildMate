package com.viratcodes.projects.BuildMate.service.impl;

import com.viratcodes.projects.BuildMate.dto.project.ProjectRequest;
import com.viratcodes.projects.BuildMate.dto.project.ProjectResponse;
import com.viratcodes.projects.BuildMate.dto.project.ProjectSummaryResponse;
import com.viratcodes.projects.BuildMate.entity.Project;
import com.viratcodes.projects.BuildMate.entity.ProjectMember;
import com.viratcodes.projects.BuildMate.entity.ProjectMemberId;
import com.viratcodes.projects.BuildMate.entity.User;
import com.viratcodes.projects.BuildMate.enums.ProjectRole;
import com.viratcodes.projects.BuildMate.error.ResourceNotFoundException;
import com.viratcodes.projects.BuildMate.mapper.ProjectMapper;
import com.viratcodes.projects.BuildMate.repository.ProjectMemberRepository;
import com.viratcodes.projects.BuildMate.repository.ProjectRepository;
import com.viratcodes.projects.BuildMate.repository.UserRepository;
import com.viratcodes.projects.BuildMate.security.AuthUtils;
import com.viratcodes.projects.BuildMate.service.ProjectService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {

    ProjectRepository projectRepository;

    UserRepository userRepository;

    ProjectMapper projectMapper;

    ProjectMemberRepository projectMemberRepository;

    AuthUtils authUtils;

    @Override
    public ProjectResponse createProject(ProjectRequest request) {

        Long userId = authUtils.getCurrentUserId();

        User owner = userRepository.findById(userId).orElseThrow(() -> new
                ResourceNotFoundException("User", userId.toString()));
        Project project = Project.builder()
                .name(request.name())
                .isPublic(false)
                .build();
        project = projectRepository.save(project);
        ProjectMemberId projectMemberId = new ProjectMemberId(project.getId(), owner.getId());
        ProjectMember projectMember = ProjectMember.builder()
                .id(projectMemberId)
                .projectRole(ProjectRole.OWNER)
                .user(owner)
                .acceptedAt(Instant.now())
                .invitedAt(Instant.now())
                .project(project)
                .build();
        projectMemberRepository.save(projectMember);

        return projectMapper.toProjectResponse(project);

    }

    @Override
    public List<ProjectSummaryResponse> getUserProjects() {

//        return projectRepository.findAllAccessibleByUser(userId)
//                .stream()
//                .map(projectMapper::toProjectSummaryResponse)
//                .collect(Collectors.toList());
        Long userId = authUtils.getCurrentUserId();
        var projects = projectRepository.findAllAccessibleByUser(userId);
        return projectMapper.toListOfProjectSummaryResponse(projects);
    }

    @Override
    @PreAuthorize("@security.canViewProject(#projectId)")
    public ProjectResponse getUserProjectsById(Long projectId) {

        Long userId = authUtils.getCurrentUserId();
        Project project = getAccessibleProjectById(projectId, userId);
        return projectMapper.toProjectResponse(project);

    }

    @Override
    @PreAuthorize("@security.canEditProject(#projectId)")
    public ProjectResponse updateProject(Long projectId, ProjectRequest request) {

        Long userId = authUtils.getCurrentUserId();

        Project project = getAccessibleProjectById(projectId, userId);

        project.setName(request.name());
        project = projectRepository.save(project);
        return projectMapper.toProjectResponse(project);
    }

    @Override
    @PreAuthorize("@security.canDeleteProject(#projectId)")
    public void softDelete(Long id) {

        Long userId = authUtils.getCurrentUserId();

        Project project = getAccessibleProjectById(id, userId);

        project.setDeletedAt(Instant.now());
        projectRepository.save(project);
    }

    ///  INTERNAL FUNCTIONS

    public Project getAccessibleProjectById(Long projectId, Long userId) {

        return projectRepository.findAccessibleProjectById(projectId, userId).orElseThrow(
                () -> new ResourceNotFoundException("Project", projectId.toString()));
    }

}
