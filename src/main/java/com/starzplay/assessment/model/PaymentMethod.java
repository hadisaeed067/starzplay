package com.starzplay.assessment.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "payment_method")
public class PaymentMethod {
    @Id
    private String name;
    private String displayName;
    private String paymentType;
    @OneToMany(mappedBy = "paymentMethod")
    private List<PaymentPlan> paymentPlans;
}
