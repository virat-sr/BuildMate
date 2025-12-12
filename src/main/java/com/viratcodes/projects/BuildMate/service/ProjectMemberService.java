package com.viratcodes.projects.BuildMate.service;

import com.viratcodes.projects.BuildMate.dto.member.InviteMemberRequest;
import com.viratcodes.projects.BuildMate.dto.member.MemberResponse;

import java.util.List;

public interface ProjectMemberService {

    List<MemberResponse> getProjectMembers(Long projectId, Long userId);

    MemberResponse inviteMember(Long projectId, InviteMemberRequest request, Long userId);

    MemberResponse updateMemberRole(Long projectId, Long memberId, InviteMemberRequest request, Long userId);

    void removeProjectMember(Long projectId, Long memberId, Long userId);

}
