package com.food.ordering.system.order.service.messaging.mapper;


import java.util.*;
import org.springframework.stereotype.*;
import com.food.ordering.system.domain.valueObject.OrderApprovalStatus;
import com.food.ordering.system.domain.valueObject.PaymentStatus;
import com.food.ordering.system.kafka.order.avro.model.*;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;
import com.food.ordering.system.service.domain.dto.message.PaymentResponse;
import com.food.ordering.system.service.domain.dto.message.RestaurantApprovalResponse;

/**
 * @author juliwolf
 */

@Component
public class OrderMessagingDataMapper {
  public PaymentRequestAvroModel orderCreatedEventToPaymentRequestAvroModel (OrderCreatedEvent orderCreatedEvent) {
    Order order = orderCreatedEvent.getOrder();

    return PaymentRequestAvroModel.newBuilder()
      .setId(UUID.randomUUID())
      .setSagaId(null)
      .setCustomerId(order.getCustomerId().getValue())
      .setOrderId(order.getId().getValue())
      .setPrice(order.getPrice().getAmount())
      .setCreatedAt(orderCreatedEvent.getCreatedAt().toInstant())
      .setPaymentOrderStatus(PaymentOrderStatus.PENDING)
      .build();
  }

  public PaymentRequestAvroModel orderCancelledEventToPaymentRequestAvroModel (OrderCancelledEvent orderCancelledEvent) {
    Order order = orderCancelledEvent.getOrder();

    return PaymentRequestAvroModel.newBuilder()
      .setId(UUID.randomUUID())
      .setSagaId(null)
      .setCustomerId(order.getCustomerId().getValue())
      .setOrderId(order.getId().getValue())
      .setPrice(order.getPrice().getAmount())
      .setCreatedAt(orderCancelledEvent.getCreatedAt().toInstant())
      .setPaymentOrderStatus(PaymentOrderStatus.CANCELLED)
      .build();
  }

  public RestaurantApprovalRequestAvroModel orderPaidEventToRestaurantRequestAvroModel (OrderPaidEvent orderPaidEvent) {
    Order order = orderPaidEvent.getOrder();

    return RestaurantApprovalRequestAvroModel.newBuilder()
      .setId(UUID.randomUUID())
      .setSagaId(null)
      .setOrderId(order.getId().getValue())
      .setRestaurantId(order.getRestaurantId().getValue())
      .setRestaurantOrderStatus(RestaurantOrderStatus.valueOf(order.getOrderStatus().name()))
      .setProducts(
        order.getItems().stream()
          .map(orderItem ->
            Product.newBuilder()
              .setId(orderItem.getProduct().getId().getValue().toString())
              .setQuantity(orderItem.getQuantity())
              .build()
          ).toList()
      )
      .setPrice(order.getPrice().getAmount())
      .setCreatedAt(orderPaidEvent.getCreatedAt().toInstant())
      .setRestaurantOrderStatus(RestaurantOrderStatus.PAID)
      .build();
  }

  public PaymentResponse paymentResponseAvroModelToPaymentResponse (PaymentResponseAvroModel paymentResponseAvroModel) {
    return PaymentResponse.builder()
      .id(paymentResponseAvroModel.getId())
      .sagaId(paymentResponseAvroModel.getSagaId())
      .paymentId(paymentResponseAvroModel.getPaymentId())
      .customerId(paymentResponseAvroModel.getCustomerId())
      .orderId(paymentResponseAvroModel.getOrderId())
      .price(paymentResponseAvroModel.getPrice())
      .createdAt(paymentResponseAvroModel.getCreatedAt())
      .paymentStatus(PaymentStatus.valueOf(paymentResponseAvroModel.getPaymentStatus().name()))
      .failureMessages(paymentResponseAvroModel.getFailureMessages())
      .build();
  }

  public RestaurantApprovalResponse restaurantApprovalResponseAvroModelToRestaurantApprovalResponse (RestaurantApprovalResponseAvroModel restaurantApprovalResponseAvroModel) {
    return RestaurantApprovalResponse.builder()
      .id(restaurantApprovalResponseAvroModel.getId())
      .sagaId(restaurantApprovalResponseAvroModel.getSagaId())
      .orderId(restaurantApprovalResponseAvroModel.getOrderId())
      .restaurantId(restaurantApprovalResponseAvroModel.getRestaurantId())
      .createdAt(restaurantApprovalResponseAvroModel.getCreatedAt())
      .orderApprovalStatus(OrderApprovalStatus.valueOf(restaurantApprovalResponseAvroModel.getOrderApprovalStatus().name()))
      .failureMessages(restaurantApprovalResponseAvroModel.getFailureMessages())
      .build();
  }
}
