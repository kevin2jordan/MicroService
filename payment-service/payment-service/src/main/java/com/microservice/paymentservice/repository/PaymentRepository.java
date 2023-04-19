package com.microservice.paymentservice.repository;

import com.microservice.paymentservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    public Payment findById(String id);

    List<Payment> findByPaymentStatus(String status);
}
