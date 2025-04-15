package com.mystore.orders.controller;

import com.mystore.orders.dto.OrderRequest;
import com.mystore.orders.dto.OrderResponse;
import com.mystore.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {


    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder")
    public OrderResponse placeOrder(@RequestBody OrderRequest orderRequest){

        return orderService.placeOrder(orderRequest);
    }

    @GetMapping("/sayHi")
    public ResponseEntity<String> sayHi(@RequestParam("name") String name) {
        return new ResponseEntity<>(orderService.callGreeting(name), HttpStatus.FOUND);
    }
}