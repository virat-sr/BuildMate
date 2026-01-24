package com.viratcodes.projects.BuildMate.service.impl;

import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.viratcodes.projects.BuildMate.dto.subscription.CheckoutRequest;
import com.viratcodes.projects.BuildMate.dto.subscription.CheckoutResponse;
import com.viratcodes.projects.BuildMate.dto.subscription.PortalResponse;
import com.viratcodes.projects.BuildMate.entity.Plan;
import com.viratcodes.projects.BuildMate.entity.User;
import com.viratcodes.projects.BuildMate.error.ResourceNotFoundException;
import com.viratcodes.projects.BuildMate.repository.PlanRepository;
import com.viratcodes.projects.BuildMate.repository.UserRepository;
import com.viratcodes.projects.BuildMate.security.AuthUtils;
import com.viratcodes.projects.BuildMate.service.PaymentProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class StripePaymentProcessor implements PaymentProcessor {

    private final AuthUtils authUtils;

    private final PlanRepository planRepository;

    private final UserRepository userRepository;

    @Value("${client.url}")
    private String frontendUrl;

    @Override
    public CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request) {

        Plan plan = planRepository.findById(request.planId()).orElseThrow(()->
                new ResourceNotFoundException("Plan", request.planId().toString()));
        Long userId = authUtils.getCurrentUserId();
        User user = userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("User", userId.toString()));

        var params = SessionCreateParams.builder()
                .addLineItem(
                        SessionCreateParams.LineItem.builder().setPrice(plan.getStripePriceId()).setQuantity(1L).build())
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setSubscriptionData(
                        new SessionCreateParams.SubscriptionData.Builder()
                                .setBillingMode(SessionCreateParams.SubscriptionData.BillingMode.builder()
                                        .setType(SessionCreateParams.SubscriptionData.BillingMode.Type.FLEXIBLE)
                                        .build())
                                .build()
                )
                .setSuccessUrl(frontendUrl + "/success.html?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(frontendUrl + "/cancel.html")
                .putMetadata("user_id", userId.toString())
                .putMetadata("plan_id", plan.getId().toString());

        try {
            String stripeCustomerId = user.getStripeCustomerId();
            if(stripeCustomerId == null || stripeCustomerId.isEmpty()) {
                params.setCustomerEmail(user.getUsername());
            } else {
                params.setCustomer(stripeCustomerId);
            }
            Session session = Session.create(params.build());
            return new CheckoutResponse(session.getUrl());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public PortalResponse openCustomerPortal(Long userId) {

        return null;
    }

    @Override
    public void handleWebhookEvent(String type, StripeObject stripeObject, Map<String, String> metadata) {
        log.info("type");

    }

}
