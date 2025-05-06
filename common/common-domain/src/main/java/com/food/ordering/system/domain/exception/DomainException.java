package com.food.ordering.system.domain.exception;


/**
 * @author juliwolf
 */

public class DomainException extends RuntimeException {
  public DomainException (String message) {
    super(message);
  }

  public DomainException (String message, Throwable cause) {
    super(message, cause);
  }
}
