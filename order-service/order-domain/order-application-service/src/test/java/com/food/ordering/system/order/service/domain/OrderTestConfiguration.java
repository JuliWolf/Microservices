package com.food.ordering.system.order.service.domain;


import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.*;
import org.mockito.Mockito;
import com.food.ordering.system.service.domain.ports.output.message.publisher.payment.OrderCancelledPaymentMessagePublisher;
import com.food.ordering.system.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.food.ordering.system.service.domain.ports.output.message.publisher.restaurantApproval.OrderPaidRestaurantRequestMessagePublisher;
import com.food.ordering.system.service.domain.ports.output.repository.CustomerRepository;
import com.food.ordering.system.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.service.domain.ports.output.repository.RestaurantRepository;

/**
 * @author juliwolf
 */

@SpringBootApplication(scanBasePackages = "com.food.ordering.system")
public class OrderTestConfiguration {

  @Bean
  public OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher () {
    return Mockito.mock(OrderCreatedPaymentRequestMessagePublisher.class);
  }

  @Bean
  public OrderCancelledPaymentMessagePublisher orderCancelledPaymentMessagePublisher () {
    return Mockito.mock(OrderCancelledPaymentMessagePublisher.class);
  }

  @Bean
  public OrderPaidRestaurantRequestMessagePublisher orderPaidRestaurantRequestMessagePublisher () {
    return Mockito.mock(OrderPaidRestaurantRequestMessagePublisher.class);
  }

  @Bean
  public OrderRepository orderRepository () {
    return Mockito.mock(OrderRepository.class);
  }

  @Bean
  public CustomerRepository customerRepository () {
    return Mockito.mock(CustomerRepository.class);
  }

  @Bean
  public RestaurantRepository restaurantRepository () {
    return Mockito.mock(RestaurantRepository.class);
  }

  @Bean
  public OrderDomainService orderDomainService () {
    return new OrderDomainServiceImpl();
  }
}
