package com.viratcodes.projects.BuildMate.dto.chat;

import com.viratcodes.projects.BuildMate.enums.ChatEventType;

public record ChatEventResponse(
        Long id,
        ChatEventType type,
        Integer sequenceOrder,
        String content,
        String filePath,
        String metaData
) {

}
