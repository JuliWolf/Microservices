package com.food.ordering.system.service.domain.ports.output.message.publisher.payment;


import com.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent;

/**
 * @author juliwolf
 */

public interface OrderCancelledPaymentMessagePublisher extends DomainEventPublisher<OrderCancelledEvent> {
}
