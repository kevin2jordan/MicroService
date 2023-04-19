package com.microservice.paymentservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microservice.paymentservice.entity.Payment;
import com.microservice.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/doPayment")
    public Payment doPayment(@RequestBody Payment payment) throws JsonProcessingException {

        return paymentService.doPayment(payment);
    }

    @GetMapping("/{id}")
    public Payment getPaymentById(@PathVariable String id) throws JsonProcessingException {
        return paymentService.getPaymentById(id);
    }

    @GetMapping("/status/{status}")
    public List<Payment> getPaymentByStatus(@PathVariable String status) {
        return paymentService.getPaymentByStatus(status);
    }

}
