package com.food.ordering.system.order.service.dataaccess.order.mapper;


import java.util.*;
import org.springframework.stereotype.*;
import com.food.ordering.system.domain.valueObject.*;
import com.food.ordering.system.order.service.dataaccess.order.entity.OrderAddressEntity;
import com.food.ordering.system.order.service.dataaccess.order.entity.OrderEntity;
import com.food.ordering.system.order.service.dataaccess.order.entity.OrderItemEntity;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.OrderItem;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.valueObject.OrderItemId;
import com.food.ordering.system.order.service.domain.valueObject.StreetAddress;
import com.food.ordering.system.order.service.domain.valueObject.TrackingId;

import static com.food.ordering.system.order.service.domain.entity.Order.FAILURE_MESSAGE_DELIMETER;

/**
 * @author juliwolf
 */

@Component
public class OrderDataAccessMapper {
  public OrderEntity orderToOrderEntity(Order order) {
    OrderEntity orderEntity = OrderEntity.builder()
      .orderId(order.getId().getValue())
      .customerId(order.getCustomerId().getValue())
      .restaurantId(order.getRestaurantId().getValue())
      .trackingId(order.getTrackingId().getValue())
      .address(deliveryAddressToAddressEntity(order.getDeliveryAddress()))
      .price(order.getPrice().getAmount())
      .items(orderItemsToOrderItemEntities(order.getItems()))
      .orderStatus(order.getOrderStatus())
      .failureMessage(
        order.getFailureMessages() != null
          ? String.join(FAILURE_MESSAGE_DELIMETER, order.getFailureMessages())
          : null
      )
      .build();

    orderEntity.getAddress().setOrder(orderEntity);
    orderEntity.getItems().forEach(orderItemEntity -> orderItemEntity.setOrderEntity(orderEntity));

    return orderEntity;
  }

  public Order orderEntityToOrder(OrderEntity orderEntity) {
    return Order.Builder.builder()
      .orderId(new OrderId(orderEntity.getOrderId()))
      .customerId(new CustomerId(orderEntity.getCustomerId()))
      .restaurantId(new RestaurantId(orderEntity.getRestaurantId()))
      .deliveryAddress(addressEntityToDeliveryAddress(orderEntity.getAddress()))
      .price(new Money(orderEntity.getPrice()))
      .items(orderItemEntitiesToOrderItems(orderEntity.getItems()))
      .trackingId(new TrackingId(orderEntity.getTrackingId()))
      .orderStatus(orderEntity.getOrderStatus())
      .failureMessages(
        orderEntity.getFailureMessage().isEmpty()
          ? new ArrayList<>()
          : new ArrayList<>(Arrays.asList(orderEntity.getFailureMessage().split(FAILURE_MESSAGE_DELIMETER)))
      )
      .build();
  }

  private List<OrderItem> orderItemEntitiesToOrderItems (List<OrderItemEntity> items) {
    return items.stream()
      .map(orderItemEntity ->
        OrderItem.Builder.builder()
          .orderItemId(new OrderItemId(orderItemEntity.getOrderItemId()))
          .product(new Product(new ProductId(orderItemEntity.getProductId())))
          .price(new Money(orderItemEntity.getPrice()))
          .quantity(orderItemEntity.getQuantity())
          .subTotal(new Money(orderItemEntity.getSubTotal()))
          .build()
      )
      .toList();
  }

  private StreetAddress addressEntityToDeliveryAddress (OrderAddressEntity address) {
    return new StreetAddress(
      address.getOrderAddressId(),
      address.getStreet(),
      address.getPostalCode(),
      address.getCity()
    );
  }

  private List<OrderItemEntity> orderItemsToOrderItemEntities (List<OrderItem> items) {
    return items.stream()
      .map(orderItem ->
        OrderItemEntity.builder()
          .orderItemId(orderItem.getId().getValue())
          .productId(orderItem.getProduct().getId().getValue())
          .price(orderItem.getPrice().getAmount())
          .quantity(orderItem.getQuantity())
          .subTotal(orderItem.getSubTotal().getAmount())
          .build()
      ).toList();
  }

  private OrderAddressEntity deliveryAddressToAddressEntity (StreetAddress deliveryAddress) {
    return OrderAddressEntity.builder()
      .orderAddressId(deliveryAddress.getId())
      .street(deliveryAddress.getStreet())
      .postalCode(deliveryAddress.getPostalCode())
      .city(deliveryAddress.getCity())
      .build();
  }
}
