package com.viratcodes.projects.BuildMate.dto.subscription;

public record PlanResponse(
        Long id,
        String name,
        String stripePriceId,
        Integer maxProjects,
        Integer maxTokensPerDay,
        Integer maxPrevious,
        Boolean unLimitedAi,
        Boolean active
) {

}
