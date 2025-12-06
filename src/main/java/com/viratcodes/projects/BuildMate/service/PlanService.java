package com.viratcodes.projects.BuildMate.service;

import com.viratcodes.projects.BuildMate.dto.subscription.PlanResponse;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface PlanService {

    List<PlanResponse> getAllActivePlans();

}
