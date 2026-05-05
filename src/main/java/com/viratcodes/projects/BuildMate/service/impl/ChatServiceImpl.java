package com.viratcodes.projects.BuildMate.service.impl;

import com.viratcodes.projects.BuildMate.dto.chat.ChatResponse;
import com.viratcodes.projects.BuildMate.entity.ChatMessage;
import com.viratcodes.projects.BuildMate.entity.ChatSession;
import com.viratcodes.projects.BuildMate.entity.ChatSessionId;
import com.viratcodes.projects.BuildMate.mapper.ChatMapper;
import com.viratcodes.projects.BuildMate.repository.ChatMessageRepository;
import com.viratcodes.projects.BuildMate.repository.ChatSessionRepository;
import com.viratcodes.projects.BuildMate.security.AuthUtils;
import com.viratcodes.projects.BuildMate.service.ChatService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final AuthUtils authUtils;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatSessionRepository chatSessionRepository;
    private final ChatMapper chatMapper;

    @Override
    public List<ChatResponse> getProjectChatHistory(Long projectId) {

        Long userId = authUtils.getCurrentUserId();
        ChatSession chatSession = chatSessionRepository.getReferenceById(new ChatSessionId(projectId, userId));

        List<ChatMessage> chatMessageList = chatMessageRepository.findByChatSession(chatSession);

        return  chatMapper.fromListOfChatMessage(chatMessageList);
    }

}
