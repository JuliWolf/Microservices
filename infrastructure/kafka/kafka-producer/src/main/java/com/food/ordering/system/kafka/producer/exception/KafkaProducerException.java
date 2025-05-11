package com.food.ordering.system.kafka.producer.exception;


/**
 * @author juliwolf
 */

public class KafkaProducerException extends RuntimeException{
  public KafkaProducerException (String message) {
    super(message);
  }
}
