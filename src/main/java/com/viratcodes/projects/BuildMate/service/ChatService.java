package com.viratcodes.projects.BuildMate.service;

import com.viratcodes.projects.BuildMate.dto.chat.ChatResponse;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface ChatService {


    List<ChatResponse> getProjectChatHistory(Long projectId);

}
