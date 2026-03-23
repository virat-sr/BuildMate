package com.viratcodes.projects.BuildMate.service.impl;

import com.viratcodes.projects.BuildMate.llm.PromptUtils;
import com.viratcodes.projects.BuildMate.llm.advisors.FileTreeContextAdvisor;
import com.viratcodes.projects.BuildMate.llm.tools.CodeGenerationTools;
import com.viratcodes.projects.BuildMate.security.AuthUtils;
import com.viratcodes.projects.BuildMate.service.AiGenerationService;
import com.viratcodes.projects.BuildMate.service.ProjectFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiGenerationServiceImpl implements AiGenerationService {

    private static final Pattern FILE_TAG_PATTERN = Pattern.compile("<file path=\"([^\"]+)\">(.*?)</file>", Pattern.DOTALL);

    private final ChatClient chatClient;

    private final AuthUtils authUtils;

    private final ProjectFileService projectFileService;

    private final FileTreeContextAdvisor fileTreeContextAdvisor;

    @Override
    @PreAuthorize("@security.canEditProject(#projectId)") // don't know what does it do here - check
    public Flux<String> streamResponse(String userMessage, Long projectId) {

        Long userId = authUtils.getCurrentUserId();
        createChatSessionIfNotExists(projectId, userId);

        Map<String, Object> advisorParams = Map.of(
                "userId", userId,
                "projectId", projectId
        );

        StringBuilder fullResponseBuffer = new StringBuilder();

        CodeGenerationTools codeGenerationTools = new CodeGenerationTools(projectFileService, projectId);

        return chatClient.prompt()
                .system(PromptUtils.CODE_GENERATION_SYSTEM_PROMPT)
                .user(userMessage)
                .tools(codeGenerationTools)
                .advisors(advisorSpec -> {
                    advisorSpec.params(advisorParams);
                    advisorSpec.advisors(fileTreeContextAdvisor);
                })
                .stream()
                .chatResponse()
                .doOnNext(response -> {
                    String content = response.getResult().getOutput().getText();
                    fullResponseBuffer.append(content);
                })
                .doOnComplete(() -> {
                    Schedulers.boundedElastic().schedule(() -> {
                        parseAndSaveFiles(fullResponseBuffer.toString(), projectId);
                    });
                })
                .doOnError(error -> log.error("Error during streaming for projectId: {}", projectId))
                .map(response -> Objects.requireNonNull(response.getResult().getOutput().getText()));

    }

    private void parseAndSaveFiles(String fullResponse, Long projectId) {

        Matcher matcher = FILE_TAG_PATTERN.matcher(fullResponse);
        while (matcher.find()) {
            String filePath = matcher.group(1);
            String fileContent = matcher.group(2).trim();

            projectFileService.saveFile(projectId, filePath, fileContent);
        }
    }

    private void createChatSessionIfNotExists(Long projectId, Long userId) {

    }

}
