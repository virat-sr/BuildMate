package com.viratcodes.projects.BuildMate.controller;

import com.viratcodes.projects.BuildMate.dto.project.ProjectRequest;
import com.viratcodes.projects.BuildMate.dto.project.ProjectResponse;
import com.viratcodes.projects.BuildMate.dto.project.ProjectSummaryResponse;
import com.viratcodes.projects.BuildMate.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/projects")
@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectSummaryResponse>> getMyProjects() {

        Long userId = 1L;
        return ResponseEntity.ok(projectService.getUserProjects(userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id) {

        Long userId = 1L;
        return ResponseEntity.ok(projectService.getUserProjectsById(id, userId));
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@RequestBody @Valid ProjectRequest request) {

        Long userId = 1L;
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(request, userId));
    }

    @PatchMapping
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id, @RequestBody @Valid ProjectRequest request) {

        Long userId = 1L;
        return ResponseEntity.ok(projectService.updateProject(id, request, userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {

        Long userId = 1L;
        projectService.softDelete(id, userId);
        return ResponseEntity.noContent().build();
    }

}
