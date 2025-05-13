package com.food.ordering.system.order.service.dataaccess.restaurant.repository;


import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import com.food.ordering.system.order.service.dataaccess.restaurant.entity.RestaurantEntity;
import com.food.ordering.system.order.service.dataaccess.restaurant.entity.RestaurantEntityId;

/**
 * @author juliwolf
 */

@Repository
public interface RestaurantJpaRepository extends JpaRepository<RestaurantEntity, RestaurantEntityId> {
  Optional<List<RestaurantEntity>> findByRestaurantIdAndProductIdIn(UUID restaurantId, List<UUID> productIds);
}
