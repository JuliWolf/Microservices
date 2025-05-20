package com.food.ordering.system.payment.service.domain.config;


import org.springframework.boot.context.properties.*;
import org.springframework.context.annotation.*;
import lombok.Data;

/**
 * @author juliwolf
 */

@Data
@Configuration
@ConfigurationProperties(prefix="payment-service")
public class PaymentServiceConfigData {
  private String paymentRequestTopicName;
  private String paymentResponseTopicName;
}
