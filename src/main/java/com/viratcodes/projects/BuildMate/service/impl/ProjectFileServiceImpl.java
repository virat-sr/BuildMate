package com.viratcodes.projects.BuildMate.service.impl;

import com.viratcodes.projects.BuildMate.dto.project.FileContentResponse;
import com.viratcodes.projects.BuildMate.dto.project.FileNode;
import com.viratcodes.projects.BuildMate.entity.Project;
import com.viratcodes.projects.BuildMate.entity.ProjectFile;
import com.viratcodes.projects.BuildMate.error.ResourceNotFoundException;
import com.viratcodes.projects.BuildMate.mapper.ProjectFileMapper;
import com.viratcodes.projects.BuildMate.repository.ProjectFileRepository;
import com.viratcodes.projects.BuildMate.repository.ProjectRepository;
import com.viratcodes.projects.BuildMate.service.ProjectFileService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;


@RequiredArgsConstructor
@Service
@Slf4j
public class ProjectFileServiceImpl implements ProjectFileService {

    private static final String BUCKET_NAME = "projects";

    private final ProjectRepository projectRepository;

    private final ProjectFileRepository projectFileRepository;

    private final MinioClient minioClient;

    private final ProjectFileMapper projectFileMapper;

    @Value("${minio.project-bucket}")
    private String projectBucket;


    @Override
    public List<FileNode> getFileTree(Long projectId) {

        List<ProjectFile> projectFileList = projectFileRepository.findByProjectId(projectId);
        return projectFileMapper.toListOfFileNode(projectFileList);
    }

    @Override
    public FileContentResponse getFileContent(Long projectId, String path) {

        String objectName = projectId + "/" + path;
        try (InputStream is = minioClient.getObject(GetObjectArgs.builder()
                .bucket(BUCKET_NAME)
                .object(objectName)
                .build())) {
            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            return new FileContentResponse(path, content);


        } catch (Exception e) {
            log.error("Failed to read file: {}/{}", projectId, path, e);
            throw new RuntimeException("Failed to read file content", e);
        }
    }

    @Override
    public void saveFile(Long projectId, String path, String content) {

        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project", projectId.toString()));
        String cleanPath = path.startsWith("/") ? path.substring(1) : path;
        String objectKey = projectId + "/" + cleanPath;
        try {
            byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
            InputStream inputStream = new ByteArrayInputStream(contentBytes);
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(projectBucket)
                            .object(objectKey)
                            .stream(inputStream, contentBytes.length, -1)
                            .contentType(determineContentType(path))
                            .build()
            );

            ProjectFile file = projectFileRepository.findByProjectIdAndPath(projectId, cleanPath)
                    .orElseGet(() -> ProjectFile
                            .builder()
                            .path(cleanPath)
                            .project(project)
                            .minioObject(objectKey)
                            .createdAt(Instant.now())
                            .build());
            file.setUpdatedAt(Instant.now());
            projectFileRepository.save(file);
            log.info("Saved File:{}", objectKey);

        } catch (Exception e) {
            log.error("Failed to save file {}/{}", projectId, cleanPath, e);
            throw new RuntimeException("File save Failed", e);
        }

    }

    private String determineContentType(String path) {

        String type = URLConnection.guessContentTypeFromName(path);
        if (type != null) return type;
        if (path.endsWith(".jsx") || path.endsWith(".ts") || path.endsWith(".tsx")) return "text/javascript";
        if (path.endsWith(".json")) return "application/json";
        if (path.endsWith(".css")) return "text/css";

        return "text/plain";

    }

}
