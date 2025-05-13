package com.food.ordering.system.order.service.domain;


import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.domain.*;
import org.springframework.data.jpa.repository.config.*;

/**
 * @author juliwolf
 */

@EnableJpaRepositories(basePackages = "com.food.ordering.system.order.service.data-access")
@EntityScan(basePackages = "com.food.ordering.system.order.service.data-access")
@SpringBootApplication(scanBasePackages = "com.food.ordering.system")
public class OrderServiceApplication {
  public static void main (String[] args) {
    SpringApplication.run(OrderServiceApplication.class, args);
  }
}
