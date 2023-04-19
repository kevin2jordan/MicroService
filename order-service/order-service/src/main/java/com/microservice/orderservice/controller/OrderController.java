package com.microservice.orderservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microservice.orderservice.commons.Payment;
import com.microservice.orderservice.commons.TransactionRequest;
import com.microservice.orderservice.commons.TransactionResponse;
import com.microservice.orderservice.entity.Order;
import com.microservice.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@RestController
@RefreshScope
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/bookOrder")
    public TransactionResponse bookOrder(@RequestBody TransactionRequest request) throws JsonProcessingException {

        return orderService.saveOrder(request);
    }

    @GetMapping("/orderDetail")
    public String getOrder() {
        return "Response form get call";
    }
}
