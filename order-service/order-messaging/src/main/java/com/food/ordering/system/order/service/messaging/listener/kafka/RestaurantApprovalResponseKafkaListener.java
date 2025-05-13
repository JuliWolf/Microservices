package com.food.ordering.system.order.service.messaging.listener.kafka;


import java.util.*;
import org.springframework.kafka.annotation.*;
import org.springframework.kafka.support.*;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.*;
import com.food.ordering.system.kafka.consumer.KafkaConsumer;
import com.food.ordering.system.kafka.order.avro.model.OrderApprovalStatus;
import com.food.ordering.system.kafka.order.avro.model.PaymentResponseAvroModel;
import com.food.ordering.system.kafka.order.avro.model.RestaurantApprovalResponseAvroModel;
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper;
import com.food.ordering.system.service.domain.ports.input.message.listener.restaurantApproval.RestaurantApprovalResponseMessageListener;
import lombok.extern.slf4j.Slf4j;

import static com.food.ordering.system.order.service.domain.entity.Order.FAILURE_MESSAGE_DELIMETER;

/**
 * @author juliwolf
 */

@Slf4j
@Component
public class RestaurantApprovalResponseKafkaListener implements KafkaConsumer<RestaurantApprovalResponseAvroModel> {
  private final RestaurantApprovalResponseMessageListener restaurantApprovalResponseMessageListener;
  private final OrderMessagingDataMapper orderMessagingDataMapper;

  public RestaurantApprovalResponseKafkaListener (
    RestaurantApprovalResponseMessageListener restaurantApprovalResponseMessageListener,
    OrderMessagingDataMapper orderMessagingDataMapper
  ) {
    this.restaurantApprovalResponseMessageListener = restaurantApprovalResponseMessageListener;
    this.orderMessagingDataMapper = orderMessagingDataMapper;
  }

  @Override
  @KafkaListener(
    id = "${kafka-consumer-config.restaurant-approval-consumer-group-id}",
    topics = "${order-service.restaurant-approval-response-topic-name}"
  )
  public void receive (
    @Payload List<RestaurantApprovalResponseAvroModel> messages,
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

    messages.forEach(restaurantApprovalResponseAvroModel -> {
      if (OrderApprovalStatus.APPROVED == restaurantApprovalResponseAvroModel.getOrderApprovalStatus()) {
        log.info("Processing approval response for order id {}", restaurantApprovalResponseAvroModel.getOrderId());

        restaurantApprovalResponseMessageListener.orderApproved(
          orderMessagingDataMapper.restaurantApprovalResponseAvroModelToRestaurantApprovalResponse(restaurantApprovalResponseAvroModel)
        );
      } else if (OrderApprovalStatus.REJECTED == restaurantApprovalResponseAvroModel.getOrderApprovalStatus()) {
        log.info(
          "Processing ejected order for order id: {}, with failure message: {}",
          restaurantApprovalResponseAvroModel.getOrderId(),
          String.join(FAILURE_MESSAGE_DELIMETER, restaurantApprovalResponseAvroModel.getFailureMessages())
        );

        restaurantApprovalResponseMessageListener.orderRejected(
          orderMessagingDataMapper.restaurantApprovalResponseAvroModelToRestaurantApprovalResponse(restaurantApprovalResponseAvroModel)
        );
      }
    });
  }
}
