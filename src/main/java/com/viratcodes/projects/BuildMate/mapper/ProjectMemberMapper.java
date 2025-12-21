package com.viratcodes.projects.BuildMate.mapper;

import com.viratcodes.projects.BuildMate.dto.member.MemberResponse;
import com.viratcodes.projects.BuildMate.entity.ProjectMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMemberMapper {

    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "userId", source = "user.id")
    MemberResponse toProjectMemberResponseFromMember(ProjectMember projectMember);

}
