package com.viratcodes.projects.BuildMate.service.impl;

import com.viratcodes.projects.BuildMate.dto.project.ProjectRequest;
import com.viratcodes.projects.BuildMate.dto.project.ProjectResponse;
import com.viratcodes.projects.BuildMate.dto.project.ProjectSummaryResponse;
import com.viratcodes.projects.BuildMate.entity.Project;
import com.viratcodes.projects.BuildMate.entity.User;
import com.viratcodes.projects.BuildMate.mapper.ProjectMapper;
import com.viratcodes.projects.BuildMate.repository.ProjectRepository;
import com.viratcodes.projects.BuildMate.repository.UserRepository;
import com.viratcodes.projects.BuildMate.service.ProjectService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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

    @Override
    public ProjectResponse createProject(ProjectRequest request, Long userId) {

        User owner = userRepository.findById(userId).orElseThrow();
        Project project = Project.builder()
                .name(request.name())
                .owner(owner)
                .isPublic(false)
                .build();
        project = projectRepository.save(project);
        return projectMapper.toProjectResponse(project);

    }

    @Override
    public List<ProjectSummaryResponse> getUserProjects(Long userId) {

//        return projectRepository.findAllAccessibleByUser(userId)
//                .stream()
//                .map(projectMapper::toProjectSummaryResponse)
//                .collect(Collectors.toList());
        var projects = projectRepository.findAllAccessibleByUser(userId);
        return projectMapper.toListOfProjectSummaryResponse(projects);
    }

    @Override
    public ProjectResponse getUserProjectsById(Long id, Long userId) {

        return null;
    }

    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest request, Long userId) {

        Project project = getAccessibleProjectById(id, userId);
        if (!project.getOwner().getId().equals(userId)) {
            throw new RuntimeException("You are not allowed to Update the name");
        }
        project.setName(request.name());
        project = projectRepository.save(project);
        return projectMapper.toProjectResponse(project);
    }

    @Override
    public void softDelete(Long id, Long userId) {

        Project project = getAccessibleProjectById(id, userId);
        if (!project.getOwner().getId().equals(userId)) {
            throw new RuntimeException("You are not allowed to Delete");
        }
        project.setDeletedAt(Instant.now());
        projectRepository.save(project);
    }

    ///  INTERNAL FUNCTIONS

    public Project getAccessibleProjectById(Long projectId, Long userId) {

        return projectRepository.findAccessibleProjectById(projectId, userId).orElseThrow();
    }

}
