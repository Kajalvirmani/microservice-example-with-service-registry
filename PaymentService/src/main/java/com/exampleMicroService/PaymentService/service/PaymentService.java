package com.exampleMicroService.PaymentService.service;


import com.exampleMicroService.PaymentService.model.PaymentRequest;
import com.exampleMicroService.PaymentService.model.PaymentResponse;
import org.springframework.http.HttpStatusCode;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentResponseByOrderId(String orderId);
}
