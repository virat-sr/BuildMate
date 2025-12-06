package com.viratcodes.projects.BuildMate.dto.subscription;

public record PlanLimitResponse(
        String planName,
        int maxTokenPerDay,
        int maxProjects,
        boolean unlimitedAi
) {

}
