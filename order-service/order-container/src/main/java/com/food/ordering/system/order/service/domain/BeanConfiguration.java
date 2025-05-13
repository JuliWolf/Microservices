package com.food.ordering.system.order.service.domain;


import org.springframework.context.annotation.*;

/**
 * @author juliwolf
 */

@Configuration
public class BeanConfiguration {

  @Bean
  public OrderDomainService orderDomainService () {
    return new OrderDomainServiceImpl();
  }
}
