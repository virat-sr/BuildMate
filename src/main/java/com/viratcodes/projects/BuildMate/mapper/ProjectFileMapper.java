package com.viratcodes.projects.BuildMate.mapper;

import ch.qos.logback.core.model.ComponentModel;
import com.viratcodes.projects.BuildMate.dto.project.FileNode;
import com.viratcodes.projects.BuildMate.entity.ProjectFile;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectFileMapper {

    List<FileNode> toListOfFileNode(List<ProjectFile> projectFileList);
}
