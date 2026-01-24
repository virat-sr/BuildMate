package com.viratcodes.projects.BuildMate.service;

import com.viratcodes.projects.BuildMate.dto.subscription.CheckoutRequest;
import com.viratcodes.projects.BuildMate.dto.subscription.CheckoutResponse;
import com.viratcodes.projects.BuildMate.dto.subscription.PortalResponse;


public interface SubscriptionService {

    SubscriptionService getCurrentSubscription(Long userId);


}
