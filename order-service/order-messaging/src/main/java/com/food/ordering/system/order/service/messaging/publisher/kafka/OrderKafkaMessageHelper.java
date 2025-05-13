package com.food.ordering.system.order.service.messaging.publisher.kafka;


import java.util.function.*;
import org.springframework.kafka.support.*;
import org.springframework.stereotype.*;
import org.apache.kafka.clients.producer.RecordMetadata;
import com.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author juliwolf
 */

@Slf4j
@Component
public class OrderKafkaMessageHelper {
  public <T> BiConsumer<SendResult<String, T>, Throwable> getKafkaCallback (
    String responseTopicName,
    T requestAvroModel,
    String orderId,
    String requestAvroModelName
  ) {
    return (result, throwable) -> {
      if (throwable != null) {
        log.error("Error while sending {} message {} to topic {}", requestAvroModelName, requestAvroModel.toString(), responseTopicName, throwable);
        return;
      }

      RecordMetadata metadata = result.getRecordMetadata();
      log.info(
        "Received successful response from Kafka for order id: {} Topic: {} Partition: {} Offset: {} Timestamp: {}",
        orderId,
        metadata.topic(),
        metadata.partition(),
        metadata.offset(),
        metadata.timestamp()
      );
    };
  }
}
