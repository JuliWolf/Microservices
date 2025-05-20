package com.food.ordering.system.payment.service.domain.exception;


import com.food.ordering.system.domain.exception.DomainException;

/**
 * @author juliwolf
 */

public class PaymentApplicationServiceException extends DomainException {
  public PaymentApplicationServiceException (String message) {
    super(message);
  }

  public PaymentApplicationServiceException (String message, Throwable cause) {
    super(message, cause);
  }
}
