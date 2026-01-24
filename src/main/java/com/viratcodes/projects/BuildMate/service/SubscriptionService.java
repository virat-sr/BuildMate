package com.viratcodes.projects.BuildMate.service;

import com.viratcodes.projects.BuildMate.dto.subscription.SubscriptionResponse;
import com.viratcodes.projects.BuildMate.enums.SubscriptionStatus;

import java.time.Instant;


public interface SubscriptionService {

    SubscriptionResponse getCurrentSubscription();

    void activateSubscription(Long userId, Long planId, String subcriptionId, String customerId);

    void updateSubscription(String subscriptionId, SubscriptionStatus status, Instant periodEnd, Boolean cancelAtPeriodEnd, Long planId);

    void cancelSubscription(String subscriptionId);

    void renewSubscription(String subId, Instant periodStart, Instant periodEnd);

    void markSubscriptionPastDue(String subId);


}
