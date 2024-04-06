package com.exampleMicroservice.ProductService.service;

import com.exampleMicroservice.ProductService.entity.Product;
import com.exampleMicroservice.ProductService.exception.ProductServiceCustomException;
import com.exampleMicroservice.ProductService.model.ProductRequest;
import com.exampleMicroservice.ProductService.model.ProductResponse;
import com.exampleMicroservice.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Addin product");
        Product product= Product.builder().productName(productRequest.getName())
                .quantitiy(productRequest.getQuantity())
                .price(productRequest.getPrice()).build();
        productRepository.save(product);
        log.info("product created");
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
        log.info("get the product with productId :{}",productId);

       Product product= productRepository.findById(productId)
               .orElseThrow(()-> new ProductServiceCustomException("product with given id not found", "NOT_FOUND"));
       ProductResponse productResponse= new ProductResponse();

        BeanUtils.copyProperties(product, productResponse);
        return productResponse;
    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
        log.info("reduce quantity of id {} by quantity {}",quantity,productId);
        Product product= productRepository.findById(productId)
                .orElseThrow(()-> new ProductServiceCustomException("product with given id not found", "NOT_FOUND"));
        if(product.getQuantitiy()< quantity){
            throw new ProductServiceCustomException("product does not have sufficient quantity","INSUFFICIENT_QUANTITY");
        }
        else{
            product.setQuantitiy(product.getQuantitiy()-quantity);
            productRepository.save(product);
            log.info("product quantity reduced successfully");
        }
    }
}
