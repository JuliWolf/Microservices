package com.food.ordering.system.order.service.domain.event;


import java.time.ZonedDateTime;
import com.food.ordering.system.domain.event.DomainEvent;
import com.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.service.domain.entity.Order;

/**
 * @author juliwolf
 */

public class OrderPaidEvent extends OrderEvent {
  private final DomainEventPublisher<OrderPaidEvent> orderPaidEventDomainEventPublisher;

  public OrderPaidEvent(
    Order order,
    ZonedDateTime createdAt,
    DomainEventPublisher<OrderPaidEvent> orderPaidEventDomainEventPublisher
  ) {
    super(order, createdAt);

    this.orderPaidEventDomainEventPublisher = orderPaidEventDomainEventPublisher;
  }

  @Override
  public void fire () {
    orderPaidEventDomainEventPublisher.publish(this);
  }
}
