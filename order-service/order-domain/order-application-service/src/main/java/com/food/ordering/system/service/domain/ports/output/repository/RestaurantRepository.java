package com.food.ordering.system.service.domain.ports.output.repository;


import java.util.*;
import com.food.ordering.system.order.service.domain.entity.Restaurant;

/**
 * @author juliwolf
 */

public interface RestaurantRepository {

  Optional<Restaurant> findRestaurantInformation (Restaurant restaurant);
}
