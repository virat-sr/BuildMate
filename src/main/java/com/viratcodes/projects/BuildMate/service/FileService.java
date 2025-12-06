package com.viratcodes.projects.BuildMate.service;

import com.viratcodes.projects.BuildMate.dto.project.FileContentResponse;
import com.viratcodes.projects.BuildMate.dto.project.FileNode;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface FileService {

    List<FileNode> getFileTree(Long projectId, Long userId);

    FileContentResponse getFileContent(Long projectId, String path, Long userId);

}
