package com.starzplay.assessment.dto;

import com.starzplay.assessment.model.PaymentMethod;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentDto {
    private List<PaymentMethod> paymentMethods;
}
