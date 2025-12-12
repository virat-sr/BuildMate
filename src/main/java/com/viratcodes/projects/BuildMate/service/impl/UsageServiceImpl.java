package com.viratcodes.projects.BuildMate.service.impl;

import com.viratcodes.projects.BuildMate.dto.subscription.PlanLimitResponse;
import com.viratcodes.projects.BuildMate.dto.subscription.UsageTodayResponse;
import com.viratcodes.projects.BuildMate.service.UsageService;
import org.springframework.stereotype.Service;

@Service
public class UsageServiceImpl implements UsageService {

    @Override
    public UsageTodayResponse getTodayUsageOfUser(Long userId) {

        return null;
    }

    @Override
    public PlanLimitResponse getCurrentSubscriptionLimitOfUser(Long userId) {

        return null;
    }

}
