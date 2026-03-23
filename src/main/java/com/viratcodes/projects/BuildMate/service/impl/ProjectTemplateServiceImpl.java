package com.viratcodes.projects.BuildMate.service.impl;

import com.viratcodes.projects.BuildMate.entity.Project;
import com.viratcodes.projects.BuildMate.entity.ProjectFile;
import com.viratcodes.projects.BuildMate.error.ResourceNotFoundException;
import com.viratcodes.projects.BuildMate.repository.ProjectFileRepository;
import com.viratcodes.projects.BuildMate.repository.ProjectRepository;
import com.viratcodes.projects.BuildMate.service.ProjectFileService;
import com.viratcodes.projects.BuildMate.service.ProjectTemplateService;
import io.minio.*;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProjectTemplateServiceImpl implements ProjectTemplateService {

    private final ProjectFileRepository projectFileRepository;

    private static final String TEMPLATE_BUCKET = "starter-projects";

    private static final String TARGET_BUCKET = "projects";

    private static final String TEMPLATE_NAME = "react-vite-tailwind-daisyui-starter";

    private final MinioClient minioClient;

    private final ProjectFileService projectFileService;

    private final ProjectRepository projectRepository;

    @Override
    public void initializeProjectFromTemplate(Long projectId) {

        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new ResourceNotFoundException("Project", projectId.toString())
        );

        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(TEMPLATE_BUCKET)
                            .prefix(TEMPLATE_NAME + "/")
                            .recursive(true)
                            .build()
            );
            List<ProjectFile> fileToSave = new ArrayList<>();

            for (Result<Item> result : results) {
                Item item = result.get();
                String sourceKey = item.objectName();

                String cleanPath = sourceKey.replaceFirst(TEMPLATE_NAME + "/", "");
                String deskKey = projectId + "/" + cleanPath;

                minioClient.copyObject(
                        CopyObjectArgs.builder()
                                .bucket(TARGET_BUCKET)
                                .object(deskKey)
                                .source(
                                        CopySource.builder()
                                                .bucket(TEMPLATE_BUCKET)
                                                .object(sourceKey)
                                                .build()
                                )
                                .build()
                );
                ProjectFile pf = ProjectFile.builder()
                        .project(project)
                        .path(cleanPath)
                        .minioObject(deskKey)
                        .createdAt(Instant.now())
                        .updatedAt(Instant.now())
                        .build();

                fileToSave.add(pf);

            }
            projectFileRepository.saveAll(fileToSave);

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize project from template.", e);
        }

    }

}
