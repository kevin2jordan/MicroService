package com.microservice.orderservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.orderservice.commons.Payment;
import com.microservice.orderservice.commons.TransactionRequest;
import com.microservice.orderservice.commons.TransactionResponse;
import com.microservice.orderservice.entity.Order;
import com.microservice.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
@RefreshScope
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;


    static Logger log = LoggerFactory.getLogger(OrderService.class);
   /* {
        BasicConfigurator.configure();
    }*/
    @Autowired
    @Lazy
    private RestTemplate restTemplate;

    @Value("${microservice.payment-service.endpoints.endpoint.uri}")
    private String ENDPOINT_URL;
    public TransactionResponse saveOrder(TransactionRequest request) throws JsonProcessingException {
        String response = "";
        Order order = request.getOrder();
        Payment payment = request.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());

        log.error("ENDPOINT_URL is " + ENDPOINT_URL);
        log.info("Order-Service Request : "+new ObjectMapper().writeValueAsString(request));


        Payment paymentRespone = restTemplate.postForObject(ENDPOINT_URL, payment, Payment.class);
        response = paymentRespone.getPaymentStatus().equals("success") ? "Payment processing successful and order is placed" : "There is a failure in payment API, Order is added to the cart";

        orderRepository.save(order);
        log.info("Order Service getting Response from Payment-Service : "+new ObjectMapper().writeValueAsString(response));

        return new TransactionResponse(order, paymentRespone.getAmount(), paymentRespone.getTransactionId(), response);
    }
}
