package com.food.ordering.system.order.service.dataaccess.order.entity;


import java.io.Serializable;
import java.util.*;
import lombok.*;

/**
 * @author juliwolf
 */

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemEntityId implements Serializable {
  private Long orderItemId;
  private OrderEntity order;

  @Override
  public boolean equals (Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    OrderItemEntityId that = (OrderItemEntityId) o;
    return Objects.equals(orderItemId, that.orderItemId) && Objects.equals(order, that.order);
  }

  @Override
  public int hashCode () {
    return Objects.hash(orderItemId, order);
  }
}
