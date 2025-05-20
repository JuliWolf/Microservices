package com.food.ordering.system.payment.service.domain.exception;


import com.food.ordering.system.domain.exception.DomainException;

/**
 * @author juliwolf
 */

public class PaymentDomainException extends DomainException {
  public PaymentDomainException (String message) {
    super(message);
  }
}
