package com.viratcodes.projects.BuildMate.entity;

import com.viratcodes.projects.BuildMate.enums.MessageRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatMessage {

    Long id;

    ChatSession chatSession;

    String context;

    MessageRole role;

    String toolCalls; // JSON array of tools called

    Integer tokensUsed;

    Instant createdAt;

}
