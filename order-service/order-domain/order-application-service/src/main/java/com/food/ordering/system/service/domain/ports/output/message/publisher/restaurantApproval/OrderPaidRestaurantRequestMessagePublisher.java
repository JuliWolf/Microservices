package com.food.ordering.system.service.domain.ports.output.message.publisher.restaurantApproval;


import com.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;

/**
 * @author juliwolf
 */

public interface OrderPaidRestaurantRequestMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {
}
