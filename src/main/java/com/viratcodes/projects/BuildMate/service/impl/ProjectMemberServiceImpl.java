package com.viratcodes.projects.BuildMate.service.impl;

import com.viratcodes.projects.BuildMate.dto.member.InviteMemberRequest;
import com.viratcodes.projects.BuildMate.dto.member.MemberResponse;
import com.viratcodes.projects.BuildMate.entity.Project;
import com.viratcodes.projects.BuildMate.entity.ProjectMember;
import com.viratcodes.projects.BuildMate.entity.ProjectMemberId;
import com.viratcodes.projects.BuildMate.entity.User;
import com.viratcodes.projects.BuildMate.mapper.ProjectMemberMapper;
import com.viratcodes.projects.BuildMate.repository.ProjectMemberRepository;
import com.viratcodes.projects.BuildMate.repository.ProjectRepository;
import com.viratcodes.projects.BuildMate.repository.UserRepository;
import com.viratcodes.projects.BuildMate.service.ProjectMemberService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Transactional
public class ProjectMemberServiceImpl implements ProjectMemberService {

    ProjectMemberRepository projectMemberRepository;

    ProjectRepository projectRepository;

    ProjectMemberMapper projectMemberMapper;

    UserRepository userRepository;

    @Override
    public List<MemberResponse> getProjectMembers(Long projectId, Long userId) {

        Project project = getAccessibleProjectById(projectId, userId);
        List<MemberResponse> memberResponseList = new ArrayList<>();
        memberResponseList.add(projectMemberMapper.toProjectMemberResponseFromOwner(project.getOwner()));
        memberResponseList.addAll(
                projectMemberRepository.findByIdProjectId(projectId)
                        .stream()
                        .map((member) -> projectMemberMapper.toProjectMemberResponseFromMember(member))
                        .toList());
        return memberResponseList;
    }

    @Override
    public MemberResponse inviteMember(Long projectId, InviteMemberRequest request, Long userId) {

        Project project = getAccessibleProjectById(projectId, userId);
        if (!project.getOwner().getId().equals(userId)) {
            throw new RuntimeException("Not allowed");
        }
        User invitee = userRepository.findByEmail(request.email()).orElseThrow();
        if (invitee.getId().equals(userId)) {
            throw new RuntimeException("Cannot invite yourself");
        }
        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, invitee.getId());
        ProjectMember member = ProjectMember.builder()
                .id(projectMemberId)
                .project(project)
                .user(invitee)
                .projectRole(request.role())
                .invitedAt(Instant.now())
                .build();
        projectMemberRepository.save(member);
        return projectMemberMapper.toProjectMemberResponseFromMember(member);

    }

    @Override
    public MemberResponse updateMemberRole(Long projectId, Long memberId, InviteMemberRequest request, Long userId) {

        Project project = getAccessibleProjectById(projectId, userId);

        if (!project.getOwner().getId().equals(userId)) {
            throw new RuntimeException("NOT ALLOWED");
        }
        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, memberId);
        ProjectMember projectMember = projectMemberRepository.findById(projectMemberId).orElseThrow();

        projectMember.setProjectRole(request.role());
        projectMemberRepository.save(projectMember); // Transaction is there, this might not be explicitly needed

        return projectMemberMapper.toProjectMemberResponseFromMember(projectMember);

    }

    @Override
    public void removeProjectMember(Long projectId, Long memberId, Long userId) {

        Project project = getAccessibleProjectById(projectId, userId);
        if (!project.getOwner().getId().equals(userId)) {
            throw new RuntimeException("NOT ALLOWED");
        }
        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, memberId);
        if (!projectMemberRepository.existsById(projectMemberId)) {
            throw new RuntimeException("MEMBER NOT FOUND IN PROJECT");
        }
        projectMemberRepository.deleteById(projectMemberId);


    }

    // INTERNAL FUNCTIONS
    public Project getAccessibleProjectById(Long projectId, Long userId) {

        return projectRepository.findAccessibleProjectById(projectId, userId).orElseThrow();
    }

}
