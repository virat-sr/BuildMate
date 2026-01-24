package com.viratcodes.projects.BuildMate.service;

import com.stripe.model.StripeObject;
import com.viratcodes.projects.BuildMate.dto.subscription.CheckoutRequest;
import com.viratcodes.projects.BuildMate.dto.subscription.CheckoutResponse;
import com.viratcodes.projects.BuildMate.dto.subscription.PortalResponse;
import org.jspecify.annotations.Nullable;

import java.util.Map;

public interface PaymentProcessor {

    CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request);

    PortalResponse openCustomerPortal();

    void handleWebhookEvent(String type, StripeObject stripeObject, Map<String, String> metadata);

}
