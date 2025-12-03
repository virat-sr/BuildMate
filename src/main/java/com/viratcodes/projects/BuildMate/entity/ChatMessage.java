package com.viratcodes.projects.BuildMate.entity;

import java.time.Instant;

public class ChatMessage {
    Long id;
    ChatSession chatSession;
    String context;

    String textCalls;
    Integer tokensUsed;

    Instant createdAt;
}
