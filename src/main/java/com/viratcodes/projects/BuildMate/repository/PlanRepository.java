package com.viratcodes.projects.BuildMate.repository;

import com.viratcodes.projects.BuildMate.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    Optional<Plan> findByStripePriceId(String id);

}
