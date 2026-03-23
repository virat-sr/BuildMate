package com.viratcodes.projects.BuildMate.llm.tools;

import com.viratcodes.projects.BuildMate.service.ProjectFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class CodeGenerationTools {

    private final ProjectFileService projectFileService;

    private final Long projectId;

    @Tool
    public List<String> readFile(List<String> paths) {

        List<String> result = new ArrayList<>();

        for (String path : paths) {
            String cleanPath = path.startsWith("/") ? path.substring(1) : path;
            log.info("Requested File : {}", cleanPath);
            String content = projectFileService.getFileContent(projectId, cleanPath).content();
            result.add(String.format(
                    "------START OF FILE: %s --- \n%s\n -- END OF FILE ---",
                    cleanPath, content
            ));

        }

        return result;

    }


}
