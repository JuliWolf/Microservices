package com.food.ordering.system.payment.service.domain.event;


import java.time.ZonedDateTime;
import java.util.*;
import com.food.ordering.system.payment.service.domain.entity.Payment;

/**
 * @author juliwolf
 */

public class PaymentCancelledEvent extends PaymentEvent{
  public PaymentCancelledEvent (
    Payment payment,
    ZonedDateTime createdAt
  ) {
    super(payment, createdAt, Collections.emptyList());
  }
}
