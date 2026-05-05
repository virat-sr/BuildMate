package com.viratcodes.projects.BuildMate.dto.chat;

import com.viratcodes.projects.BuildMate.entity.ChatEvent;
import com.viratcodes.projects.BuildMate.entity.ChatSession;
import com.viratcodes.projects.BuildMate.enums.MessageRole;

import java.time.Instant;
import java.util.List;

public record ChatResponse(
        Long id,
        ChatSession chatSession,
        MessageRole role,
        List<ChatEvent> events,
        String content,
        Integer tokenUsed,
        Instant createdAt

) {

}
