package com.food.ordering.system.order.service.messaging.publisher.kafka;


import org.springframework.stereotype.*;
import com.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel;
import com.food.ordering.system.kafka.order.avro.model.RestaurantApprovalRequestAvroModel;
import com.food.ordering.system.kafka.producer.service.KafkaProducer;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper;
import com.food.ordering.system.service.domain.config.OrderServiceConfigData;
import com.food.ordering.system.service.domain.ports.output.message.publisher.restaurantApproval.OrderPaidRestaurantRequestMessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author juliwolf
 */

@Slf4j
@Component
public class PayOrderKafkaMessagePublisher implements OrderPaidRestaurantRequestMessagePublisher {
  private final OrderMessagingDataMapper orderMessagingDataMapper;
  private final OrderServiceConfigData orderServiceConfigData;
  private final OrderKafkaMessageHelper orderKafkaMessageHelper;
  private final KafkaProducer<String, RestaurantApprovalRequestAvroModel> kafkaProducer;

  public PayOrderKafkaMessagePublisher (
    OrderMessagingDataMapper orderMessagingDataMapper,
    OrderServiceConfigData orderServiceConfigData,
    OrderKafkaMessageHelper orderKafkaMessageHelper,
    KafkaProducer<String, RestaurantApprovalRequestAvroModel> kafkaProducer
  ) {
    this.orderMessagingDataMapper = orderMessagingDataMapper;
    this.orderServiceConfigData = orderServiceConfigData;
    this.orderKafkaMessageHelper = orderKafkaMessageHelper;
    this.kafkaProducer = kafkaProducer;
  }

  @Override
  public void publish (OrderPaidEvent domainEvent) {
    String orderId = domainEvent.getOrder().getId().getValue().toString();

    try {
      RestaurantApprovalRequestAvroModel restaurantApprovalRequestAvroModel = orderMessagingDataMapper.orderPaidEventToRestaurantRequestAvroModel(domainEvent);

      kafkaProducer.send(
        orderServiceConfigData.getPaymentRequestTopicName(),
        orderId,
        restaurantApprovalRequestAvroModel,
        orderKafkaMessageHelper.getKafkaCallback(
          orderServiceConfigData.getPaymentRequestTopicName(),
          restaurantApprovalRequestAvroModel,
          orderId,
          "RestaurantApprovalRequestAvroModel"
        )
      );

      log.info("RestaurantApprovalRequestAvroModel send to Kafka for order id: {}", restaurantApprovalRequestAvroModel.getOrderId());
    } catch (Exception e) {
      log.error("Error while sending RestaurantApprovalRequestAvroModel message to kafka wit order id: {}, error: {}", orderId, e.getMessage());
    }
  }
}
