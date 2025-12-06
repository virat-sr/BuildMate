package com.viratcodes.projects.BuildMate.service;

import com.viratcodes.projects.BuildMate.dto.subscription.CheckoutRequest;
import com.viratcodes.projects.BuildMate.dto.subscription.CheckoutResponse;
import com.viratcodes.projects.BuildMate.dto.subscription.PortalResponse;
import org.jspecify.annotations.Nullable;

public interface SubscriptionService {

    SubscriptionService getCurrentSubscription(Long userId);

    CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request, Long userId);

    PortalResponse openCustomerPortal(Long userId);

}
