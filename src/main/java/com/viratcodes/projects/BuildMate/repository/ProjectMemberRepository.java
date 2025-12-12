package com.viratcodes.projects.BuildMate.repository;

import com.viratcodes.projects.BuildMate.entity.ProjectMember;
import com.viratcodes.projects.BuildMate.entity.ProjectMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {

    List<ProjectMember> findByIdProjectId(Long projectId);
}
