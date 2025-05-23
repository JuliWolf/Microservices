package com.food.ordering.system.payment.service.domain.event;


import java.time.ZonedDateTime;
import java.util.*;
import com.food.ordering.system.domain.event.DomainEvent;
import com.food.ordering.system.payment.service.domain.entity.Payment;

/**
 * @author juliwolf
 */

public abstract class PaymentEvent implements DomainEvent<Payment> {
  private final Payment payment;
  private final ZonedDateTime createdAt;
  private final List<String> failureMessages;

  public Payment getPayment () {
    return payment;
  }

  public ZonedDateTime getCreatedAt () {
    return createdAt;
  }

  public List<String> getFailureMessages () {
    return failureMessages;
  }

  public PaymentEvent (Payment payment, ZonedDateTime createdAt, List<String> failureMessages) {
    this.payment = payment;
    this.createdAt = createdAt;
    this.failureMessages = failureMessages;
  }
}
