package com.starzplay.assessment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "payment_plan")
public class PaymentPlan {
    @Id
    private Integer id;
    private Double netAmount;
    private Integer taxAmount;
    private Double grossAmount;
    private String currency;
    private String duration;
    @ManyToOne
    @JoinColumn(name = "payment_method_name", nullable = false)
    @JsonIgnore
    private PaymentMethod paymentMethod;
}
