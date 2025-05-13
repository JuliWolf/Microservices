package com.food.ordering.system.kafka.consumer;


import java.util.*;
import org.apache.avro.specific.SpecificRecordBase;

/**
 * @author juliwolf
 */

public interface KafkaConsumer<T extends SpecificRecordBase> {
  void receive(List<T> messages, List<String> keys, List<Integer> partitions, List<Long> offsets);
}
