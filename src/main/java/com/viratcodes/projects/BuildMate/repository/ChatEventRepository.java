package com.viratcodes.projects.BuildMate.repository;

import com.viratcodes.projects.BuildMate.entity.ChatEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatEventRepository extends JpaRepository<ChatEvent, Long> {

}
