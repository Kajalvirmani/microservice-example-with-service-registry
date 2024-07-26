package com.exampleMicroservices.CloudGateway;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import java.time.Duration;

@SpringBootApplication
@EnableDiscoveryClient
public class  CloudGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudGatewayApplication.class, args);
	}

	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer(){
		return factory-> factory.configureDefault(
				id-> new Resilience4JConfigBuilder(id)
						.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofMillis(2000)).build())
						.circuitBreakerConfig(
								CircuitBreakerConfig.ofDefaults()
						).build()
		);
	}

}
