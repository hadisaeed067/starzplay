package com.starzplay.assessment.repository;

import com.starzplay.assessment.model.PaymentPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentPlanRepository extends JpaRepository<PaymentPlan, Integer> {
}
