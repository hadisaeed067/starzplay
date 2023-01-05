package com.starzplay.assessment.controller;

import com.starzplay.assessment.dto.PaymentDto;
import com.starzplay.assessment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/payment-methods")
@Slf4j
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<Object> getPaymentMethod(@RequestParam(name = "name", required = false) String name, @RequestParam(name = "id", required = false) Integer id) {
        try {
            PaymentDto dto;
            if (name == null && id == null) {
                dto = paymentService.getPaymentMethods();
            } else if (id == null) {
                dto = paymentService.getPaymentMethodByName(name);
            } else if (name == null) {
                dto = paymentService.getPaymentMethodByPaymentPlanId(id);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(Collections.emptyList());
            }
            if (dto.getPaymentMethods().isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).contentType(MediaType.APPLICATION_JSON).body(dto);
            } else {
                return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(dto);
            }
        } catch (Exception e) {
            log.info("Request Failed, Exception [{}]", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(Collections.emptyList());
        }
    }

    @PostMapping()
    public ResponseEntity<Object> addPaymentMethod(@RequestBody PaymentDto dto) {
        try {
            String response = paymentService.addPaymentMethod(dto);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
        } catch (Exception e) {
            log.info("Request Failed, Exception [{}]", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(Collections.emptyList());
        }
    }

    @PutMapping()
    public ResponseEntity<Object> updatePaymentMethod(@RequestParam(name = "id") Integer id, @RequestBody PaymentDto dto) {
        try {
            String response = paymentService.updatePaymentMethod(id, dto);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
        } catch (Exception e) {
            log.info("Request Failed, Exception [{}]", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(Collections.emptyList());
        }
    }
}
