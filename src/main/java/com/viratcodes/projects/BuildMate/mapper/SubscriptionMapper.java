package com.viratcodes.projects.BuildMate.mapper;

import com.viratcodes.projects.BuildMate.dto.subscription.PlanResponse;
import com.viratcodes.projects.BuildMate.dto.subscription.SubscriptionResponse;
import com.viratcodes.projects.BuildMate.entity.Plan;
import com.viratcodes.projects.BuildMate.entity.Subscription;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionResponse toSubscriptionResponse(Subscription subscription);

    PlanResponse toPlanResponse(Plan plan);

}
