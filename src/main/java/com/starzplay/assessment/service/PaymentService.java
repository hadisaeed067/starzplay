package com.starzplay.assessment.service;

import com.starzplay.assessment.dto.PaymentDto;
import com.starzplay.assessment.model.PaymentMethod;
import com.starzplay.assessment.model.PaymentPlan;
import com.starzplay.assessment.repository.PaymentMethodRepository;
import com.starzplay.assessment.repository.PaymentPlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentPlanRepository paymentPlanRepository;

    public PaymentDto getPaymentMethods() {
        List<PaymentMethod> methodList = paymentMethodRepository.findAll();
        if (methodList.isEmpty()) {
            return new PaymentDto(Collections.emptyList());
        } else {
            return new PaymentDto(methodList);
        }
    }

    public PaymentDto getPaymentMethodByName(String name) {
        List<PaymentMethod> methodList = paymentMethodRepository.findAllByName(name);
        if (methodList.isEmpty()) {
            return new PaymentDto(Collections.emptyList());
        } else {
            return new PaymentDto(methodList);
        }
    }

    public PaymentDto getPaymentMethodByPaymentPlanId(Integer id) {
        Optional<PaymentPlan> paymentPlan = paymentPlanRepository.findById(id);
        if (paymentPlan.isPresent()) {
            PaymentMethod paymentMethod = paymentPlan.get().getPaymentMethod();
            List<PaymentMethod> paymentMethods = new ArrayList<>();
            paymentMethods.add(paymentMethod);
            return new PaymentDto(paymentMethods);
        } else {
            return new PaymentDto(Collections.emptyList());
        }
    }

    public String addPaymentMethod(PaymentDto dto) {
        for (PaymentMethod method : dto.getPaymentMethods()) {
            PaymentMethod paymentMethod = new PaymentMethod();
            paymentMethod.setName(method.getName());
            paymentMethod.setDisplayName(method.getDisplayName());
            paymentMethod.setPaymentType(method.getPaymentType());
            paymentMethodRepository.save(paymentMethod);
            if (method.getPaymentPlans() != null) {
                for (PaymentPlan plan : method.getPaymentPlans()) {
                    PaymentPlan paymentPlan = new PaymentPlan();
                    paymentPlan.setId(plan.getId());
                    paymentPlan.setDuration(plan.getDuration());
                    paymentPlan.setCurrency(plan.getCurrency());
                    paymentPlan.setNetAmount(plan.getNetAmount());
                    paymentPlan.setGrossAmount(plan.getGrossAmount());
                    paymentPlan.setTaxAmount(plan.getTaxAmount());
                    paymentPlan.setPaymentMethod(paymentMethod);
                    paymentPlanRepository.save(paymentPlan);
                }
            }
        }
        return "Payment Method Added Successfully";
    }

    public String updatePaymentMethod(Integer id, PaymentDto dto) {
        Optional<PaymentPlan> optionalPaymentPlan = paymentPlanRepository.findById(id);
        if (optionalPaymentPlan.isPresent()) {
            for (PaymentMethod method : dto.getPaymentMethods()) {
                PaymentMethod paymentMethod = optionalPaymentPlan.get().getPaymentMethod();
                paymentMethod.setDisplayName(method.getDisplayName());
                paymentMethod.setPaymentType(method.getPaymentType());
                paymentMethodRepository.save(paymentMethod);
                if (method.getPaymentPlans() != null) {
                    for (PaymentPlan paymentPlan : method.getPaymentPlans()) {
                        PaymentPlan newPaymentPlan = new PaymentPlan();
                        newPaymentPlan.setDuration(paymentPlan.getDuration());
                        newPaymentPlan.setCurrency(paymentPlan.getCurrency());
                        newPaymentPlan.setNetAmount(paymentPlan.getNetAmount());
                        newPaymentPlan.setGrossAmount(paymentPlan.getGrossAmount());
                        newPaymentPlan.setTaxAmount(paymentPlan.getTaxAmount());
                        newPaymentPlan.setPaymentMethod(paymentMethod);
                        paymentPlanRepository.save(paymentPlan);
                    }
                }
            }
            return "Payment Method Updated Successfully";
        } else {
            return "No Record Found Against Id=" + id;
        }
    }
}
