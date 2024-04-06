package com.exampleMicroservice.OrderService.controller;

import com.exampleMicroservice.OrderService.external.client.ProductService;
import com.exampleMicroservice.OrderService.model.OrderRequest;
import com.exampleMicroservice.OrderService.model.OrderResponse;
import com.exampleMicroservice.OrderService.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/order")
@RestController
@Log4j2
public class OrderController {

    @Autowired
     OrderService orderService;

    @PostMapping("/place-order")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest){

        //order entity--> save the data with the status order created
        //product service--> block products(reduce the quantity)
        //payment service--> payments--> success--> complete , else
        //cancelled

        long orderId= orderService.placeOrder(orderRequest);
        log.info("Order id:", orderId);
        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable long orderId) {
        OrderResponse orderResponse
                = orderService.getOrderDetails(orderId);

        return new ResponseEntity<>(orderResponse,
                HttpStatus.OK);
    }


}
