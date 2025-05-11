package com.food.ordering.system.kafka.producer.service;


import java.io.Serializable;
import java.util.concurrent.*;
import java.util.function.*;
import org.springframework.kafka.support.*;
import org.springframework.util.concurrent.*;
import org.apache.avro.specific.SpecificRecordBase;

/**
 * @author juliwolf
 */

public interface KafkaProducer <K extends Serializable, V extends SpecificRecordBase> {
  void send(String topicName, K key, V message, BiConsumer<SendResult<K, V>, Throwable> callback);
}
