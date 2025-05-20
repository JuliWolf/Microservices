package com.food.ordering.system.payment.service.domain.exception;


import com.food.ordering.system.domain.exception.DomainException;

/**
 * @author juliwolf
 */

public class PaymentNotFoundException extends DomainException {
  public PaymentNotFoundException (String message) {
    super(message);
  }

  public PaymentNotFoundException (String message, Throwable cause) {
    super(message, cause);
  }
}
