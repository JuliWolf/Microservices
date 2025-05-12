package com.food.ordering.system.order.service.dataaccess.order.entity;


import java.util.*;
import jakarta.persistence.*;
import lombok.*;

/**
 * @author juliwolf
 */

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_address")
@Entity
public class OrderAddressEntity {
  @Id
  private UUID orderAddressId;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "ORDER_ID")
  private OrderEntity order;

  private String street;

  private String postalCode;

  private String city;

  @Override
  public boolean equals (Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    OrderAddressEntity that = (OrderAddressEntity) o;
    return Objects.equals(orderAddressId, that.orderAddressId);
  }

  @Override
  public int hashCode () {
    return Objects.hashCode(orderAddressId);
  }
}
