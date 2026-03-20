package com.viratcodes.projects.BuildMate.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatSessionId implements Serializable {

    Long projectId;
    Long userid;

}
