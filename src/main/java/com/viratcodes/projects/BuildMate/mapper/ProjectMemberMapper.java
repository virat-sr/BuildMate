package com.viratcodes.projects.BuildMate.mapper;

import com.viratcodes.projects.BuildMate.dto.member.MemberResponse;
import com.viratcodes.projects.BuildMate.entity.ProjectMember;
import com.viratcodes.projects.BuildMate.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMemberMapper {

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "projectRole", constant = "OWNER")
    MemberResponse toProjectMemberResponseFromOwner(User owner);

    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "userId", source = "user.id")
    MemberResponse toProjectMemberResponseFromMember(ProjectMember projectMember);

}
