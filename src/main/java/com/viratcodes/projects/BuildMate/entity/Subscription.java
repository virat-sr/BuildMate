package com.viratcodes.projects.BuildMate.entity;

import com.viratcodes.projects.BuildMate.SubscriptionStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Subscription {
    Long id;

    User user;
    Plan plan;
    SubscriptionStatus status;
    String stripeCustomerId;
    String stripeSubscriptionId;

    Instant currentPeriodStart;
    Boolean cancelAtPeriodEnd;

    Instant createdAt;
    Instant updatedAt;

}

