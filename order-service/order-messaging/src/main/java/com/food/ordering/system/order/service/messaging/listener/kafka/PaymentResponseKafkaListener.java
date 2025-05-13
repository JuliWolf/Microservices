package com.food.ordering.system.order.service.messaging.listener.kafka;


import java.util.*;
import org.springframework.kafka.annotation.*;
import org.springframework.kafka.support.*;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.*;
import com.food.ordering.system.kafka.consumer.KafkaConsumer;
import com.food.ordering.system.kafka.order.avro.model.PaymentResponseAvroModel;
import com.food.ordering.system.kafka.order.avro.model.PaymentStatus;
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper;
import com.food.ordering.system.service.domain.ports.input.message.listener.payment.PaymentResponseMessageListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @author juliwolf
 */

@Slf4j
@Component
public class PaymentResponseKafkaListener implements KafkaConsumer<PaymentResponseAvroModel> {
  private final PaymentResponseMessageListener paymentResponseMessageListener;
  private final OrderMessagingDataMapper orderMessagingDataMapper;

  public PaymentResponseKafkaListener (
    PaymentResponseMessageListener paymentResponseMessageListener,
    OrderMessagingDataMapper orderMessagingDataMapper
  ) {
    this.paymentResponseMessageListener = paymentResponseMessageListener;
    this.orderMessagingDataMapper = orderMessagingDataMapper;
  }

  @Override
  @KafkaListener(
    id = "${kafka-consumer-config.payment-consumer-group-id}",
    topics = "${order-service.payment-response-topic-name}"
  )
  public void receive (
    @Payload List<PaymentResponseAvroModel> messages,
    @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
    @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
    @Header(KafkaHeaders.OFFSET) List<Long> offsets
  ) {
    log.info(
      "{} number of payment responses received with keys: {}, partitions: {} and offsets: {}",
      messages.size(),
      keys.size(),
      partitions.toString(),
      offsets.toString()
    );

    messages.forEach(paymentResponseAvroModel -> {
      if (PaymentStatus.COMPLETED == paymentResponseAvroModel.getPaymentStatus()) {
        log.info("Processing successful payment for order id: {}", paymentResponseAvroModel.getOrderId());
        paymentResponseMessageListener.paymentCompleted(
          orderMessagingDataMapper.paymentResponseAvroModelToPaymentResponse(paymentResponseAvroModel)
        );
        return;
      }

      log.info("Processing unsuccessful payment for order id: {}", paymentResponseAvroModel.getOrderId());
      paymentResponseMessageListener.paymentCancelled(
        orderMessagingDataMapper.paymentResponseAvroModelToPaymentResponse(paymentResponseAvroModel)
      );
    });
  }
}
