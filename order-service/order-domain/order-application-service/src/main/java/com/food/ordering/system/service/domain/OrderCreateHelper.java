package com.food.ordering.system.service.domain;


import java.util.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import com.food.ordering.system.order.service.domain.OrderDomainService;
import com.food.ordering.system.order.service.domain.entity.Customer;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.food.ordering.system.service.domain.ports.output.repository.CustomerRepository;
import com.food.ordering.system.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.service.domain.ports.output.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author juliwolf
 */

@Slf4j
@Service
public class OrderCreateHelper {
  private final OrderDomainService orderDomainService;
  private final OrderRepository orderRepository;
  private final CustomerRepository customerRepository;
  private final RestaurantRepository restaurantRepository;
  private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;

  private final OrderDataMapper orderDataMapper;

  public OrderCreateHelper (
    OrderDomainService orderDomainService,
    OrderRepository orderRepository,
    CustomerRepository customerRepository,
    RestaurantRepository restaurantRepository,
    OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher,
    OrderDataMapper orderDataMapper
  ) {
    this.orderDomainService = orderDomainService;
    this.orderRepository = orderRepository;
    this.customerRepository = customerRepository;
    this.restaurantRepository = restaurantRepository;
    this.orderCreatedPaymentRequestMessagePublisher = orderCreatedPaymentRequestMessagePublisher;
    this.orderDataMapper = orderDataMapper;
  }

  @Transactional
  public OrderCreatedEvent persistOrder (CreateOrderCommand createOrderCommand) {
    checkCustomer(createOrderCommand.getCustomerId());

    Restaurant restaurant = checkRestaurant(createOrderCommand);
    Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);

    OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restaurant, orderCreatedPaymentRequestMessagePublisher);

    saveOrder(order);

    log.info("Order Created with id: {}", orderCreatedEvent.getOrder().getId().getValue());

    return orderCreatedEvent;
  }

  private Restaurant checkRestaurant (CreateOrderCommand createOrderCommand) {
    Restaurant restaurant = orderDataMapper.createOrderCommandTiRestaurant(createOrderCommand);

    Optional<Restaurant> optionalRestaurant = restaurantRepository.findRestaurantInformation(restaurant);

    if (optionalRestaurant.isEmpty()) {
      log.warn("Could not find restaurant with restaurant id: {}", restaurant.getId());

      throw new OrderDomainException("Could not find restaurant with restaurant id: " + restaurant.getId());
    }

    return optionalRestaurant.get();
  }

  private void checkCustomer (UUID customerId) {
    Optional<Customer> customer = customerRepository.findCustomer(customerId);

    if (customer.isEmpty()) {
      log.warn("Could not find customer with id: {}", customerId);

      throw new OrderDomainException("Could not find customer with id: " + customerId);
    }
  }

  private Order saveOrder (Order order) {
    Order orderResult = orderRepository.save(order);

    if (orderResult == null) {
      log.error("Could not save order");
      throw new OrderDomainException("Could not save order");
    }

    log.info("Order is saved with id: {}", orderResult.getId().getValue());

    return order;
  }
}
