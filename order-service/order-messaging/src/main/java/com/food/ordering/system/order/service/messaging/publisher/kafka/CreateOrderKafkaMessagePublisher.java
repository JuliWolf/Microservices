package com.food.ordering.system.order.service.messaging.publisher.kafka;


import java.util.concurrent.*;
import java.util.function.*;
import org.springframework.kafka.support.*;
import org.springframework.stereotype.*;
import org.springframework.util.concurrent.*;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.checkerframework.checker.nullness.qual.Nullable;
import com.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel;
import com.food.ordering.system.kafka.producer.service.KafkaProducer;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper;
import com.food.ordering.system.service.domain.config.OrderServiceConfigData;
import com.food.ordering.system.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.google.common.util.concurrent.FutureCallback;
import lombok.extern.slf4j.Slf4j;

/**
 * @author juliwolf
 */

@Slf4j
@Component
public class CreateOrderKafkaMessagePublisher implements OrderCreatedPaymentRequestMessagePublisher {
  private final OrderMessagingDataMapper orderMessagingDataMapper;
  private final OrderServiceConfigData orderServiceConfigData;
  private final OrderKafkaMessageHelper orderKafkaMessageHelper;
  private final KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer;

  public CreateOrderKafkaMessagePublisher (
    OrderMessagingDataMapper orderMessagingDataMapper,
    OrderServiceConfigData orderServiceConfigData,
    OrderKafkaMessageHelper orderKafkaMessageHelper,
    KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer
  ) {
    this.orderMessagingDataMapper = orderMessagingDataMapper;
    this.orderServiceConfigData = orderServiceConfigData;
    this.orderKafkaMessageHelper = orderKafkaMessageHelper;
    this.kafkaProducer = kafkaProducer;
  }

  @Override
  public void publish (OrderCreatedEvent domainEvent) {
    String orderId = domainEvent.getOrder().getId().getValue().toString();
    log.info("Received OrderCreatedEvent for order Id: {}", orderId);

    try {
      PaymentRequestAvroModel paymentRequestAvroModel = orderMessagingDataMapper.orderCreatedEventToPaymentRequestAvroModel(domainEvent);

      kafkaProducer.send(
        orderServiceConfigData.getPaymentRequestTopicName(),
        orderId,
        paymentRequestAvroModel,
        orderKafkaMessageHelper.getKafkaCallback(
          orderServiceConfigData.getPaymentRequestTopicName(),
          paymentRequestAvroModel,
          orderId,
          "PaymentRequestAvroModel"
        )
      );

      log.info("PaymentRequestAvroModel send to Kafka for order id: {}", paymentRequestAvroModel.getOrderId());
    } catch (Exception e) {
      log.error("Error while sending PaymentRequestAvroModel message to kafka wit order id: {}, error: {}", orderId, e.getMessage());
    }
  }
}
