package com.viratcodes.projects.BuildMate.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Preview {
    Long id;
    Project project;

    String namespace;
    String podName;

}
