package com.food.ordering.system.order.service.domain.event;


import java.time.ZonedDateTime;
import com.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.service.domain.entity.Order;

/**
 * @author juliwolf
 */

public class OrderCreatedEvent extends OrderEvent {
  private final DomainEventPublisher<OrderCreatedEvent> orderCreatedEventDomainEventPublisher;

  public OrderCreatedEvent (
    Order order,
    ZonedDateTime createdAt,
    DomainEventPublisher<OrderCreatedEvent> orderCreatedEventDomainEventPublisher
  ) {
    super(order, createdAt);

    this.orderCreatedEventDomainEventPublisher = orderCreatedEventDomainEventPublisher;
  }

  @Override
  public void fire () {
    orderCreatedEventDomainEventPublisher.publish(this);
  }
}
