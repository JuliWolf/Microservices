package com.food.ordering.system.order.service.application.exception.handler;


import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.food.ordering.system.application.handler.ErrorDTO;
import com.food.ordering.system.application.handler.GlobalExceptionHandler;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.exception.OrderNotFoundException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author juliwolf
 */

@Slf4j
@ControllerAdvice
public class OrderGlobalExceptionHandler extends GlobalExceptionHandler {

  @ResponseBody
  @ExceptionHandler(OrderDomainException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorDTO handleException (OrderDomainException orderDomainException) {
    log.error(orderDomainException.getMessage(), orderDomainException);

    return ErrorDTO.builder()
      .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
      .message(orderDomainException.getMessage())
      .build();
  }

  @ResponseBody
  @ExceptionHandler(OrderNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorDTO handleException (OrderNotFoundException orderNotFoundException) {
    log.error(orderNotFoundException.getMessage(), orderNotFoundException);

    return ErrorDTO.builder()
      .code(HttpStatus.NOT_FOUND.getReasonPhrase())
      .message(orderNotFoundException.getMessage())
      .build();
  }
}
