package com.food.ordering.system.order.service.domain;


import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import com.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;

import static com.food.ordering.system.domain.DomainConstants.UTC;

/**
 * @author juliwolf
 */

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService {
  @Override
  public OrderCreatedEvent validateAndInitiateOrder (
    Order order,
    Restaurant restaurant,
    DomainEventPublisher<OrderCreatedEvent> orderCreatedEventDomainEventPublisher
  ) {
    validateRestaurant(restaurant);
    setOrderProductInformation(order, restaurant);

    order.validateOrder();
    order.initializeOrder();

    log.info("Order with id: {} is initialized", order.getId().getValue());

    return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of(UTC)), orderCreatedEventDomainEventPublisher);
  }

  @Override
  public OrderPaidEvent payOrder (
    Order order,
    DomainEventPublisher<OrderPaidEvent> orderPaidEventDomainEventPublisher
  ) {
    order.pay();

    log.info("Order with id: {} has been paid", order.getId().getValue());

    return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of(UTC)), orderPaidEventDomainEventPublisher);
  }

  @Override
  public void approveOrder (Order order) {
    order.approve();

    log.info("Order with id: {} has been approved", order.getId().getValue());
  }

  @Override
  public OrderCancelledEvent cancelOrderPayment (
    Order order,
    List<String> failureMessages,
    DomainEventPublisher<OrderCancelledEvent> orderCancelledEventDomainEventPublisher
  ) {
    order.initCancel(failureMessages);

    log.info("Order payment is cancelling for order id: {}", order.getId().getValue());

    return new OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of(UTC)), orderCancelledEventDomainEventPublisher);
  }

  @Override
  public void cancelOrder (Order order, List<String> failureMessages) {
    order.cancel(failureMessages);

    log.info("Order with id: {} has been cancelled", order.getId().getValue());
  }

  private void validateRestaurant (Restaurant restaurant) {
    if (!restaurant.isActive()) {
      throw new OrderDomainException("Restaurant with id " + restaurant.getId().getValue() + " is currently not active");
    }
  }

  private void setOrderProductInformation (Order order, Restaurant restaurant) {
    // TODO: use HashMap
    order.getItems().forEach(orderItem -> {
      restaurant.getProducts().forEach(restaurantProduct -> {
        Product currentProduct = orderItem.getProduct();

        if (!currentProduct.equals(restaurantProduct)) return;

        currentProduct.updateWithConfirmedNameAndPrice(restaurantProduct.getName(), restaurantProduct.getPrice());
      });
    });
  }
}
