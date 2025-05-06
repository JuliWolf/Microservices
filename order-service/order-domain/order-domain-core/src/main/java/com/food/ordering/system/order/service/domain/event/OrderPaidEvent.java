package com.food.ordering.system.order.service.domain.event;


import java.time.ZonedDateTime;
import com.food.ordering.system.domain.event.DomainEvent;
import com.food.ordering.system.order.service.domain.entity.Order;

/**
 * @author juliwolf
 */

public class OrderPaidEvent extends OrderEvent {
  public OrderPaidEvent(Order order, ZonedDateTime createdAt) {
    super(order, createdAt);
  }
}
