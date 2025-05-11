package com.food.ordering.system.kafka.producer.service.impl;


import java.io.Serializable;
import java.util.concurrent.*;
import java.util.function.*;
import org.springframework.kafka.*;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.*;
import org.springframework.stereotype.*;
import org.apache.avro.specific.SpecificRecordBase;
import jakarta.annotation.PreDestroy;
import com.food.ordering.system.kafka.producer.service.KafkaProducer;
import lombok.extern.slf4j.Slf4j;

/**
 * @author juliwolf
 */

@Slf4j
@Component
public class KafkaProducerImpl<K extends Serializable, V extends SpecificRecordBase> implements KafkaProducer<K, V> {

  private final KafkaTemplate<K, V> kafkaTemplate;

  public KafkaProducerImpl (KafkaTemplate<K, V> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void send (String topicName, K key, V message, BiConsumer<SendResult<K, V>, Throwable> callback) {
    log.info("Sending message={} to topic={}", message, topicName);

    try {
      CompletableFuture<SendResult<K, V>> future = kafkaTemplate.send(topicName, key, message);

      future.whenComplete(callback);
    } catch (KafkaException e) {
      log.error("Error on kafka producer with key: {}, message: {} and exception: {}",  key, message, e.getMessage());

      throw new com.food.ordering.system.kafka.producer.exception.KafkaProducerException("Error on kafka producer with key: " + key + ", message: " + message + " and exception: " + e.getMessage());
    }
  }

  @PreDestroy
  public void close() {
    if (kafkaTemplate != null) {
      log.info("Closing kafka producer");
      kafkaTemplate.destroy();
    }
  }
}
