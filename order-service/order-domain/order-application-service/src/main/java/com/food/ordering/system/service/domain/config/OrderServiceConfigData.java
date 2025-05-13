package com.food.ordering.system.service.domain.config;


import org.springframework.boot.context.properties.*;
import org.springframework.context.annotation.*;
import lombok.Data;

/**
 * @author juliwolf
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "order-service")
public class OrderServiceConfigData {
  private String paymentRequestTopicName;
  private String paymentResponseTopicName;
  private String restaurantApprovalRequestTopicName;
  private String restaurantApprovalResponseTopicName;
}
