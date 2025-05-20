package com.food.ordering.system.payment.service.domain.ports.output.message.publisher;


import com.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.payment.service.domain.event.PaymentFailedEvent;

/**
 * @author juliwolf
 */

public interface PaymentFailedMessagePublisher extends DomainEventPublisher<PaymentFailedEvent> {
}
