package com.food.ordering.system.order.service.dataaccess.order.entity;

import java.math.BigDecimal;
import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(OrderItemEntityId.class)
@Table(name = "order_item")
@Entity
public class OrderItemEntity {
  @Id
  private Long orderItemId;

  @Id
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "ORDER_ID")
  private OrderEntity orderEntity;

  private UUID productId;

  private BigDecimal price;

  private Integer quantity;

  private BigDecimal subTotal;

  @Override
  public boolean equals (Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    OrderItemEntity that = (OrderItemEntity) o;
    return Objects.equals(orderItemId, that.orderItemId) && Objects.equals(orderEntity, that.orderEntity);
  }

  @Override
  public int hashCode () {
    return Objects.hash(orderItemId, orderEntity);
  }
}