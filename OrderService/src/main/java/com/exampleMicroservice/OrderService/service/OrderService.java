package com.exampleMicroservice.OrderService.service;

import com.exampleMicroservice.OrderService.model.OrderRequest;
import com.exampleMicroservice.OrderService.model.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);
}
