package com.viratcodes.projects.BuildMate.controller;

import com.viratcodes.projects.BuildMate.dto.subscription.CheckoutRequest;
import com.viratcodes.projects.BuildMate.dto.subscription.CheckoutResponse;
import com.viratcodes.projects.BuildMate.dto.subscription.PlanResponse;
import com.viratcodes.projects.BuildMate.dto.subscription.PortalResponse;
import com.viratcodes.projects.BuildMate.service.PlanService;
import com.viratcodes.projects.BuildMate.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BillingController {

    private final PlanService planService;
    private final SubscriptionService subscriptionService;

    @GetMapping("/api/plans")
    public ResponseEntity<List<PlanResponse>> getAllPlans() {
        return ResponseEntity.ok(planService.getAllActivePlans());
    }

    @GetMapping("api/me/subscription")
    public ResponseEntity<SubscriptionService> getMySubscription() {
        Long userId = 1L;
        return ResponseEntity.ok(subscriptionService.getCurrentSubscription(userId));
    }

    @PostMapping("api/stripe/checkout")
    public ResponseEntity<CheckoutResponse> createCheckoutResponse(
            @RequestBody CheckoutRequest request
    ) {
        Long userId = 1L;
        return ResponseEntity.ok(subscriptionService.createCheckoutSessionUrl(request, userId));
    }

    @PostMapping("api/stripe/portal")
    public ResponseEntity<PortalResponse> openCustomerPortal() {
        Long userId = 1L;
        return ResponseEntity.ok(subscriptionService.openCustomerPortal(userId));
    }





}
