package com.viratcodes.projects.BuildMate.service.impl;

import com.viratcodes.projects.BuildMate.dto.subscription.SubscriptionResponse;
import com.viratcodes.projects.BuildMate.entity.Plan;
import com.viratcodes.projects.BuildMate.entity.Subscription;
import com.viratcodes.projects.BuildMate.entity.User;
import com.viratcodes.projects.BuildMate.enums.SubscriptionStatus;
import com.viratcodes.projects.BuildMate.error.ResourceNotFoundException;
import com.viratcodes.projects.BuildMate.mapper.SubscriptionMapper;
import com.viratcodes.projects.BuildMate.repository.PlanRepository;
import com.viratcodes.projects.BuildMate.repository.ProjectMemberRepository;
import com.viratcodes.projects.BuildMate.repository.SubscriptionRepository;
import com.viratcodes.projects.BuildMate.repository.UserRepository;
import com.viratcodes.projects.BuildMate.security.AuthUtils;
import com.viratcodes.projects.BuildMate.service.SubscriptionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final AuthUtils authUtils;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final ProjectMemberRepository projectMemberRepository;

    private final Integer FREE_TIER_PROJECTS_ALLOWED = 100;


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

        boolean exists = subscriptionRepository.existsByStripeSubscriptionId(subcriptionId);
        if(exists) return;

        User user = getUser(userId);
        Plan plan = getPlan(planId);

        Subscription subscription = Subscription.builder()
                .user(user)
                .plan(plan)
                .stripeSubscriptionId(subcriptionId)
                .status(SubscriptionStatus.INCOMPLETE)
                .build();

        subscriptionRepository.save(subscription);

    }

    @Override
    @Transactional
    public void updateSubscription(String gatewaySubscriptionId, SubscriptionStatus status, Instant periodStart, Instant periodEnd, Boolean cancelAtPeriodEnd, Long planId) {
        Subscription subscription = getSubscription(gatewaySubscriptionId);
        boolean hasSubscriptionUpdated = false;

        if(status != null && status!= subscription.getStatus()) {
            subscription.setStatus(status);
            hasSubscriptionUpdated = true;
        }

        if (periodStart != null && !periodStart.equals(subscription.getCurrentPeriodStart())) {
            subscription.setCurrentPeriodStart(periodStart);
            hasSubscriptionUpdated = true;
        }

        if (periodEnd != null && !periodEnd.equals(subscription.getCurrentPeriodEnd())) {
            subscription.setCancelAtPeriodEnd(cancelAtPeriodEnd);
            hasSubscriptionUpdated = true;
        }

        if(planId != null && !planId.equals(subscription.getPlan().getId())) {
            Plan newPlan = getPlan(planId);
            subscription.setPlan(newPlan);
            hasSubscriptionUpdated = true;
        }

        if(hasSubscriptionUpdated) {
            log.debug("Subscription has been updated : {}", gatewaySubscriptionId);
            subscriptionRepository.save(subscription);
        }
    }

    @Override
    public void cancelSubscription(String gatewaySubscriptionId) {
        Subscription subscription = getSubscription(gatewaySubscriptionId);
        subscription.setStatus(SubscriptionStatus.CANCELED);
        subscriptionRepository.save(subscription);
    }

    @Override
    public void renewSubscription(String gatewaySubscriptionId, Instant periodStart, Instant periodEnd) {
        Subscription subscription = getSubscription(gatewaySubscriptionId);

        Instant newStart = periodStart != null ? periodStart : periodEnd;
        subscription.setCurrentPeriodStart(newStart);
        subscription.setCurrentPeriodStart(periodEnd);

        if (subscription.getStatus() == SubscriptionStatus.PAST_DUE || subscription.getStatus() == SubscriptionStatus.INCOMPLETE) {
            subscription.setStatus(SubscriptionStatus.ACTIVE);
        }

        subscriptionRepository.save(subscription);

    }

    @Override
    public void markSubscriptionPastDue(String subId) {

    }

    @Override
    public boolean canCreateNewProject() {
        Long userId = authUtils.getCurrentUserId();
        SubscriptionResponse currentSubscription = getCurrentSubscription();

        int countOfOwnedProjects = projectMemberRepository.countProjectOwnedByUser(userId);

        if(currentSubscription.plan() == null) {
            return countOfOwnedProjects < FREE_TIER_PROJECTS_ALLOWED;
        }

        return countOfOwnedProjects < currentSubscription.plan().maxProjects();
    }

    // Utility Methods
    private User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", userId.toString()));
    }

    private Plan getPlan(Long planId) {
        return planRepository.findById(planId).orElseThrow(()-> new ResourceNotFoundException("Plan", planId.toString()));
    }

    private Subscription getSubscription(String gatewaySubscriptionId) {
        return subscriptionRepository.findByStripeSubscriptionId(gatewaySubscriptionId).orElseThrow(()->new ResourceNotFoundException("Subscription", gatewaySubscriptionId));
    }

}
