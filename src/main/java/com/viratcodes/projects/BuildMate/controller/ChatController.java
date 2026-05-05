package com.viratcodes.projects.BuildMate.controller;

import com.viratcodes.projects.BuildMate.dto.chat.ChatRequest;
import com.viratcodes.projects.BuildMate.dto.chat.ChatResponse;
import com.viratcodes.projects.BuildMate.service.AiGenerationService;
import com.viratcodes.projects.BuildMate.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ChatController {

    private final AiGenerationService aiGenerationService;

    private final ChatService chatService;

    public Flux<ServerSentEvent<String>> streamChat(@RequestBody ChatRequest request) {

        return aiGenerationService.streamResponse(request.message(), request.projectId())
                .map(data -> ServerSentEvent.<String>builder().build());
    }


    @GetMapping("/projects/{projectId}")
    public ResponseEntity<List<ChatResponse>> getChatHistory(@PathVariable Long projectId) {

        return ResponseEntity.ok(chatService.getProjectChatHistory(projectId));
    }


}
