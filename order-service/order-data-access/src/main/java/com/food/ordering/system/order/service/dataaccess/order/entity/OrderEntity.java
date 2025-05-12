package com.food.ordering.system.order.service.dataaccess.order.entity;


import java.math.BigDecimal;
import java.util.*;
import org.springframework.stereotype.*;
import jakarta.persistence.*;
import com.food.ordering.system.domain.valueObject.OrderStatus;
import lombok.*;

/**
 * @author juliwolf
 */

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@Entity
public class OrderEntity {
  @Id
  private UUID orderId;

  private UUID customerId;

  private UUID restaurantId;

  private UUID trackingId;

  private BigDecimal price;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  private String failureMessage;

  @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
  private OrderAddressEntity address;

  @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL)
  private List<OrderItemEntity> items;

  @Override
  public boolean equals (Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    OrderEntity that = (OrderEntity) o;
    return Objects.equals(orderId, that.orderId);
  }

  @Override
  public int hashCode () {
    return Objects.hashCode(orderId);
  }
}
