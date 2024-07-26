package com.exampleMicroservice.OrderService.service;


import com.exampleMicroservice.OrderService.entity.Order;
import com.exampleMicroservice.OrderService.exception.CustomException;
import com.exampleMicroservice.OrderService.external.client.PaymentService;
import com.exampleMicroservice.OrderService.external.client.ProductService;
import com.exampleMicroservice.OrderService.external.request.PaymentRequest;
import com.exampleMicroservice.OrderService.model.OrderRequest;
import com.exampleMicroservice.OrderService.model.OrderResponse;
import com.exampleMicroservice.OrderService.model.PaymentMode;
import com.exampleMicroservice.OrderService.repository.OrderRepository;
import com.exampleMicroservice.ProductService.model.ProductResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public long placeOrder(OrderRequest orderRequest) {

        //Order entity-> save te data wit status created
        //Product Service-> block products(reduce quantity)
        //payment service-> payemnets> success-> complete else
        //cancelled
        log.info("placing order request", orderRequest);

        productService.reduceQuantity(orderRequest.getProductId(),orderRequest.getQuantity());
        log.info(" product quantity reduced");
        Order order= Order.builder().orderAmount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();
        order = orderRepository.save(order);
        log.info("callin payment service");
        PaymentRequest paymentRequest= PaymentRequest.builder()
                .paymentMode(orderRequest.getPaymentMode())
                .orderId(order.getId())
                .amount(orderRequest.getTotalAmount())
                .build();
        String orderStatus= null;
        try{
            paymentService.doPayment(paymentRequest);
            log.info("payment done successfully");
            orderStatus = "PLACED";
        }
        catch (Exception e){
            orderStatus= "PAYMENT_FAILED";
            log.info("payment failed", e.getMessage());
        }
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        log.info("order placed successfully",order.getId());
        return order.getId();
    }

    @Override
    public OrderResponse getOrderDetails(long orderId){
        Order order= orderRepository.findById(orderId).orElseThrow(
                ()-> new CustomException("order not found","NOT_FOUND",404)
        );
//         ProductResponse productResponse= restTemplate.getForObject(
//                 "http://PRODUCT-SERVICE/product/"+ order.getProductId(),ProductResponse.class
//         );
//         PaymentResponse paymentResponse
//                = restTemplate.getForObject(
//                        "http://PAYMENT-SERVICE/payment/order/" + order.getId(),
//                PaymentResponse.class
//                );
//         OrderResponse.ProductDetails productDetails
//                 = OrderResponse.ProductDetails.builder()
//                 .productName(productResponse.getProductName())
//                 .productId(productResponse.getProductId())
//                 .price(productResponse.getPrice())
//                 .quantity(productResponse.getQuantity())
//                 .build();
//         OrderResponse.PaymentDetails paymentDetails= OrderResponse.PaymentDetails
//                 .builder()
//                 .paymentId(paymentResponse.getPaymentId())
//                 .paymentDate(paymentResponse.getPaymentDate())
//                 .paymentStatus(paymentResponse.getStatus())
//                 .build();
        OrderResponse orderResponse= OrderResponse.builder()
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .amount(order.getOrderAmount())
                .orderDate(order.getOrderDate())
                .build();
//                .productDetails(productDetails)
        return orderResponse;
    }
}
