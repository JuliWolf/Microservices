package com.food.ordering.system.payment.service.domain.event;


import java.time.ZonedDateTime;
import java.util.*;
import com.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.payment.service.domain.entity.Payment;

/**
 * @author juliwolf
 */

public class PaymentCancelledEvent extends PaymentEvent{
  private final DomainEventPublisher<PaymentCancelledEvent> paymentCancelledEventDomainEventPublisher;

  public PaymentCancelledEvent (
    Payment payment,
    ZonedDateTime createdAt,
    DomainEventPublisher<PaymentCancelledEvent> paymentCancelledEventDomainEventPublisher
  ) {
    super(payment, createdAt, Collections.emptyList());
    this.paymentCancelledEventDomainEventPublisher = paymentCancelledEventDomainEventPublisher;
  }

  @Override
  public void fire () {
    paymentCancelledEventDomainEventPublisher.publish(this);
  }
}
