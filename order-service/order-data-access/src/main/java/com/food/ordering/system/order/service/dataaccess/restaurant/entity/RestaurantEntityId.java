package com.food.ordering.system.order.service.dataaccess.restaurant.entity;


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
public class RestaurantEntityId implements Serializable {
  private UUID restaurantId;

  private UUID productId;

  @Override
  public boolean equals (Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    RestaurantEntityId that = (RestaurantEntityId) o;
    return Objects.equals(restaurantId, that.restaurantId) && Objects.equals(productId, that.productId);
  }

  @Override
  public int hashCode () {
    return Objects.hash(restaurantId, productId);
  }
}
