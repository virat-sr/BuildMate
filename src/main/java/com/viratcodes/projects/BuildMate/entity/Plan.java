package com.viratcodes.projects.BuildMate.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Plan {
    Long id;
    String name;
    String stripePriceId;
    Integer maxProjects;
    Integer maxTokensPerDay;
    Integer maxPrevious;
    Boolean unLimitedAi;

    Boolean active;
    /**
     * unLimitedAi - Unlimited access to LLM, ignores max tokens.
     *
     */
}
