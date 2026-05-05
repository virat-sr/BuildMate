package com.viratcodes.projects.BuildMate.mapper;

import com.viratcodes.projects.BuildMate.dto.chat.ChatResponse;
import com.viratcodes.projects.BuildMate.entity.ChatMessage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    List<ChatResponse> fromListOfChatMessage(List<ChatMessage> chatMessageList);

}
