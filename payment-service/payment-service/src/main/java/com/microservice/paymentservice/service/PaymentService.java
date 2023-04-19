package com.microservice.paymentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.paymentservice.entity.Payment;
import com.microservice.paymentservice.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    Logger logger= LoggerFactory.getLogger(PaymentService.class);

    public Payment doPayment(Payment payment) throws JsonProcessingException {
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setPaymentStatus(doPaymentProcessing());
        logger.info("Payment-Service Request : {}",new ObjectMapper().writeValueAsString(payment));

        return paymentRepository.save(payment);
    }

    private String doPaymentProcessing() {
        //actual payment processing
        return new Random().nextBoolean() ? "success" : "failure";
    }

    public Payment getPaymentById(String id) throws JsonProcessingException {
        Payment payment = paymentRepository.findById(Integer.valueOf(id)).get();

        logger.info("paymentService getPaymentById : {}",new ObjectMapper().writeValueAsString(payment));

        return payment;
    }

    public List<Payment> getPaymentByStatus(String status) {
        return paymentRepository.findByPaymentStatus(status);
    }
}
