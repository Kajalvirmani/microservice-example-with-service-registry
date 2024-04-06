package com.exampleMicroService.PaymentService.controller;

import com.exampleMicroService.PaymentService.model.PaymentRequest;
import com.exampleMicroService.PaymentService.model.PaymentResponse;
import com.exampleMicroService.PaymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentsController {

     @Autowired
    private PaymentService paymentService;

     @PostMapping
     public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest) {
     return new ResponseEntity<>(
             paymentService.doPayment(paymentRequest),
             HttpStatus.OK
     );
     }

     @GetMapping("/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentResponseByOrderId(@PathVariable String orderId){
         return new ResponseEntity<>(
                 paymentService.getPaymentResponseByOrderId(orderId),
                 HttpStatus.OK
         );
     }
}
