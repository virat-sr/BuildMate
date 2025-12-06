package com.viratcodes.projects.BuildMate.controller;

import com.viratcodes.projects.BuildMate.dto.subscription.PlanLimitResponse;
import com.viratcodes.projects.BuildMate.dto.subscription.UsageTodayResponse;
import com.viratcodes.projects.BuildMate.service.UsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing usage-related operations.
 * This controller handles requests related to user's usage tracking and subscription limits.
 */
@RestController
@RequestMapping("/api/usage")
@RequiredArgsConstructor
public class UsageController {
    private final UsageService usageService;

    /**
     * GET endpoint to retrieve today's usage data for a specific user.
     * Currently hardcoded to return usage data for user with ID 1.
     *
     * @return ResponseEntity containing UsageTodayResponse with today's usage data
     */
    @GetMapping("/today")
    public ResponseEntity<UsageTodayResponse> getTodayUsage() {
        Long userId = 1L; // TODO: Implement proper user authentication to get actual user ID
        return ResponseEntity.ok(usageService.getTodayUsageOfUser(userId));
    }

    /**
     * GET endpoint to retrieve the current subscription plan limits for a specific user.
     * Currently hardcoded to return plan details for user with ID 1.
     *
     * @return ResponseEntity containing PlanLimitResponse with subscription limits
     */
    @GetMapping("limits")
    public ResponseEntity<PlanLimitResponse> getPlanDetails() {
        Long userId = 1L; // TODO: Implement proper user authentication to get actual user ID
        return ResponseEntity.ok(usageService.getCurrentSubscriptionLimitOfUser(userId));
    }



}
