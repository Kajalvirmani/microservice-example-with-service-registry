package com.exampleMicroService.PaymentService.service;

import com.exampleMicroService.PaymentService.entity.TransactionDetails;
import com.exampleMicroService.PaymentService.model.PaymentMode;
import com.exampleMicroService.PaymentService.model.PaymentRequest;
import com.exampleMicroService.PaymentService.model.PaymentResponse;
import com.exampleMicroService.PaymentService.repository.TransactionDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PaymentServiceImpl implements PaymentService{
    @Autowired
    TransactionDetailsRepository transactionDetailsRepository;
    @Override
    public long doPayment(PaymentRequest paymentRequest) {
        TransactionDetails transactionDetails =
        TransactionDetails.builder()
                .paymentDate(Instant.now())
                .paymentStatus("SUCCESS")
                .paymentMode(paymentRequest.getPaymentMode().name())
                .amount(paymentRequest.getAmount())
                .referenceNumber(paymentRequest.getReferenceNumber())
                .orderId(paymentRequest.getOrderId())
                .build();
        transactionDetailsRepository.save(transactionDetails);

        return transactionDetails.getId();
    }

    @Override
    public PaymentResponse getPaymentResponseByOrderId(String orderId) {
        TransactionDetails transactionDetails= transactionDetailsRepository.findByOrderId(Long.valueOf(orderId));
        PaymentResponse paymentResponse = PaymentResponse.builder()
                .paymentId(transactionDetails.getId())
                .paymentDate(transactionDetails.getPaymentDate())
                .paymentMode(PaymentMode.valueOf(transactionDetails.getPaymentMode()))
                .orderId(transactionDetails.getOrderId())
                .status(transactionDetails.getPaymentStatus())
                .amount(transactionDetails.getAmount())
                .build();

        return paymentResponse;
    }
}
