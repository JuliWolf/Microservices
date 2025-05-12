package com.food.ordering.system.application.handler;


import java.util.stream.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author juliwolf
 */

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ResponseBody
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorDTO handleException(Exception exception) {
    log.error(exception.getMessage(), exception);

    return ErrorDTO.builder()
      .code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
      .message("Unexpected error!")
      .build();
  }

  @ResponseBody
  @ExceptionHandler(ValidationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorDTO validationException(ValidationException validationException) {
    if (validationException instanceof ConstraintViolationException) {
      String violation = extractViolationsFromException((ConstraintViolationException) validationException);
      log.error(violation, validationException);
      return ErrorDTO.builder()
        .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
        .message(violation)
        .build();
    }

    String exceptionMessage = validationException.getMessage();
    log.error(exceptionMessage, validationException);

    return ErrorDTO.builder()
      .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
      .message(exceptionMessage)
      .build();
  }

  private String extractViolationsFromException (ConstraintViolationException validationException) {
    return validationException.getConstraintViolations().stream()
      .map(ConstraintViolation::getMessage)
      .collect(Collectors.joining("--"));
  }
}
