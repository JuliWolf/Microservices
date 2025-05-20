package com.food.ordering.system.customer.service;


import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

/**
 * @author juliwolf
 */

@SpringBootApplication(scanBasePackages = "com.food.ordering.system")
public class CustomerServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(CustomerServiceApplication.class, args);
  }
}
