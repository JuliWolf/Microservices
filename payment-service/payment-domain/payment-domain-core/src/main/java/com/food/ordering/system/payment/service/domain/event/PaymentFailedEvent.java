package com.food.ordering.system.payment.service.domain.event;


import java.time.ZonedDateTime;
import java.util.*;
import com.food.ordering.system.payment.service.domain.entity.Payment;

/**
 * @author juliwolf
 */

public class PaymentFailedEvent extends PaymentEvent{
  public PaymentFailedEvent (
    Payment payment,
    ZonedDateTime createdAt,
    List<String> failureMessages
  ) {
    super(payment, createdAt, failureMessages);
  }
}
