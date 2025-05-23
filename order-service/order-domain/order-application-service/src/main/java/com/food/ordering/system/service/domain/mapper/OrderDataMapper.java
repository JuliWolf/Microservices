package com.food.ordering.system.service.domain.mapper;


import java.util.*;
import org.springframework.stereotype.*;
import com.food.ordering.system.domain.valueObject.CustomerId;
import com.food.ordering.system.domain.valueObject.Money;
import com.food.ordering.system.domain.valueObject.ProductId;
import com.food.ordering.system.domain.valueObject.RestaurantId;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.OrderItem;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.valueObject.StreetAddress;
import com.food.ordering.system.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.service.domain.dto.create.OrderAddress;
import com.food.ordering.system.service.domain.dto.track.TrackOrderResponse;

/**
 * @author juliwolf
 */

@Component
public class OrderDataMapper {
  public Restaurant createOrderCommandTiRestaurant (CreateOrderCommand createOrderCommand) {
    return Restaurant.Builder.builder()
      .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
      .products(
        createOrderCommand.getItems().stream()
          .map(orderItem ->
            new Product(new ProductId(orderItem.getProductId()))
          ).toList()
      ).build();
  }

  public Order createOrderCommandToOrder (CreateOrderCommand createOrderCommand) {
    return Order.Builder.builder()
      .customerId(new CustomerId(createOrderCommand.getCustomerId()))
      .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
      .deliveryAddress(orderAddressToStreetAddress(createOrderCommand.getAddress()))
      .price(new Money(createOrderCommand.getPrice()))
      .items(orderItemsToOrderItemEntities(createOrderCommand.getItems()))
      .build();
  }

  public CreateOrderResponse orderToCreateOrderResponse (Order order, String message) {
    return CreateOrderResponse.builder()
      .orderTrackingId(order.getTrackingId().getValue())
      .orderStatus(order.getOrderStatus())
      .message(message)
      .build();
  }

  public TrackOrderResponse orderToTrackOrderResponse (Order order) {
    return TrackOrderResponse.builder()
      .orderTrackingId(order.getTrackingId().getValue())
      .orderStatus(order.getOrderStatus())
      .failureMessages(order.getFailureMessages())
      .build();
  }

  private List<OrderItem> orderItemsToOrderItemEntities (
    List<com.food.ordering.system.service.domain.dto.create.OrderItem> orderItems
  ) {
    return orderItems.stream()
      .map(orderItem ->
        OrderItem.Builder.builder()
          .product(new Product(new ProductId(orderItem.getProductId())))
          .price(new Money(orderItem.getPrice()))
          .quantity(orderItem.getQuantity())
          .subTotal(new Money(orderItem.getSubTotal()))
          .build()
      ).toList();
  }

  private StreetAddress orderAddressToStreetAddress (OrderAddress orderAddress) {
    return new StreetAddress(
      UUID.randomUUID(),
      orderAddress.getStreet(),
      orderAddress.getPostalCode(),
      orderAddress.getCity()
    );
  }
}
