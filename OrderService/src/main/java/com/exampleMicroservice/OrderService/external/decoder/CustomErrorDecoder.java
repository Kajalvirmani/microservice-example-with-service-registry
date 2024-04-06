package com.exampleMicroservice.OrderService.external.decoder;

import com.exampleMicroservice.OrderService.exception.CustomException;
import com.exampleMicroservice.OrderService.external.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

import java.io.IOException;


@Log4j2
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response){
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("::{}", response.request().url());
        log.info("::{}", response.request().headers());

        try {
            ErrorResponse errorResponse= objectMapper.readValue(
                    response.body().asInputStream(), ErrorResponse.class);
            return new CustomException(errorResponse.getErrorMessage(),
                    errorResponse.getErrorCode(), response.status());
        } catch (IOException e) {
            throw new CustomException("internal server error","INTERNAL_SERVER_ERROR",500);
        }

    }
}
