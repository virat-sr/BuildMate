package com.viratcodes.projects.BuildMate.mapper;

import com.viratcodes.projects.BuildMate.dto.project.ProjectResponse;
import com.viratcodes.projects.BuildMate.dto.project.ProjectSummaryResponse;
import com.viratcodes.projects.BuildMate.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectResponse toProjectResponse(Project project);

    @Mapping(target = "projectName", source = "name")
    ProjectSummaryResponse toProjectSummaryResponse(Project project);

    List<ProjectSummaryResponse> toListOfProjectSummaryResponse(List<Project> projects);

}
