package com.viratcodes.projects.BuildMate.service.impl;

import com.viratcodes.projects.BuildMate.entity.Subscription;
import com.viratcodes.projects.BuildMate.enums.SubscriptionStatus;
import com.viratcodes.projects.BuildMate.mapper.SubscriptionMapper;
import com.viratcodes.projects.BuildMate.repository.PlanRepository;
import com.viratcodes.projects.BuildMate.repository.SubscriptionRepository;
import com.viratcodes.projects.BuildMate.repository.UserRepository;
import com.viratcodes.projects.BuildMate.security.AuthUtils;
import com.viratcodes.projects.BuildMate.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final AuthUtils authUtils;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;


    @Override
    public SubscriptionResponse getCurrentSubscription() {

        Long userId = authUtils.getCurrentUserId();

        var currentSubscription = subscriptionRepository.findByUserAndStatusIn(userId, Set.of(
                SubscriptionStatus.ACTIVE, SubscriptionStatus.PAST_DUE,
                SubscriptionStatus.TRIALING
        )).orElse(
                new Subscription()
        );

        return subscriptionMapper.toSubscriptionResponse(currentSubscription);
    }

    @Override
    public void activateSubscription(Long userId, Long planId, String subcriptionId, String customerId) {

    }

    @Override
    public void updateSubscription(String subscriptionId, SubscriptionStatus status, Instant periodEnd, Boolean cancelAtPeriodEnd, Long planId) {

    }

    @Override
    public void cancelSubscription(String subscriptionId) {

    }

    @Override
    public void renewSubscription(String subId, Instant periodStart, Instant periodEnd) {

    }

    @Override
    public void markSubscriptionPastDue(String subId) {

    }

}
