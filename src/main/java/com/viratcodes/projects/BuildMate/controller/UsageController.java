package com.viratcodes.projects.BuildMate.controller;

import com.viratcodes.projects.BuildMate.dto.subscription.PlanLimitResponse;
import com.viratcodes.projects.BuildMate.dto.subscription.UsageTodayResponse;
import com.viratcodes.projects.BuildMate.service.UsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usage")
@RequiredArgsConstructor
public class UsageController {
    private final UsageService usageService;

    @GetMapping("/today")
    public ResponseEntity<UsageTodayResponse> getTodayUsage() {
        Long userId = 1L;
        return ResponseEntity.ok(usageService.getTodayUsageOfUser(userId));
    }

    @GetMapping("limits")
    public ResponseEntity<PlanLimitResponse> getPlanDetails() {
        Long userId = 1L;
        return ResponseEntity.ok(usageService.getCurrentSubscriptionLimitOfUser(userId));
    }



}
