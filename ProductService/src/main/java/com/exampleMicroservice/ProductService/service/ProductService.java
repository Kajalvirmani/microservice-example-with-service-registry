package com.exampleMicroservice.ProductService.service;

import com.exampleMicroservice.ProductService.model.ProductRequest;
import com.exampleMicroservice.ProductService.model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);

    void reduceQuantity(long productId, long quantity);
}
