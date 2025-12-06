package com.viratcodes.projects.BuildMate.service;

import com.viratcodes.projects.BuildMate.dto.subscription.PlanLimitResponse;
import com.viratcodes.projects.BuildMate.dto.subscription.UsageTodayResponse;

/**
 * UsageService interface defines the contract for services that handle user usage tracking and subscription management.
 * It provides methods to retrieve current usage data and subscription limits for users.
 */
public interface UsageService {

    /**
     * Retrieves the current usage data for a specific user for today.
     *
     * @param userId The unique identifier of the user whose usage is to be retrieved
     * @return UsageTodayResponse containing the usage data for today
     */
    UsageTodayResponse getTodayUsageOfUser(Long userId);

    /**
     * Retrieves the current subscription limits for a specific user.
     *
     * @param userId The unique identifier of the user whose subscription limits are to be retrieved
     * @return PlanLimitResponse containing the current subscription limits
     */
    PlanLimitResponse getCurrentSubscriptionLimitOfUser(Long userId);

}
