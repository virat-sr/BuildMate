package com.viratcodes.projects.BuildMate.service;

import com.viratcodes.projects.BuildMate.dto.subscription.PlanLimitResponse;
import com.viratcodes.projects.BuildMate.dto.subscription.UsageTodayResponse;
import org.jspecify.annotations.Nullable;

public interface UsageService {

    UsageTodayResponse getTodayUsageOfUser(Long userId);

    PlanLimitResponse getCurrentSubscriptionLimitOfUser(Long userId);

}
