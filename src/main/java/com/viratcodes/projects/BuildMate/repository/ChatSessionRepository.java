package com.viratcodes.projects.BuildMate.repository;

import com.viratcodes.projects.BuildMate.entity.ChatSession;
import com.viratcodes.projects.BuildMate.entity.ChatSessionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatSessionRepository extends JpaRepository<ChatSession, ChatSessionId> {

}
