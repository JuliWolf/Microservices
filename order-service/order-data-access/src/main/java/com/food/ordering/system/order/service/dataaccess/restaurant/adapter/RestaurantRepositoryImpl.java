package com.food.ordering.system.order.service.dataaccess.restaurant.adapter;


import java.util.*;
import org.springframework.stereotype.*;
import com.food.ordering.system.order.service.dataaccess.restaurant.entity.RestaurantEntity;
import com.food.ordering.system.order.service.dataaccess.restaurant.mapper.RestaurantDataAccessMapper;
import com.food.ordering.system.order.service.dataaccess.restaurant.repository.RestaurantJpaRepository;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.service.domain.ports.output.repository.RestaurantRepository;

/**
 * @author juliwolf
 */

@Component
public class RestaurantRepositoryImpl implements RestaurantRepository {
  private final RestaurantJpaRepository restaurantJpaRepository;
  private final RestaurantDataAccessMapper restaurantDataAccessMapper;

  public RestaurantRepositoryImpl (
    RestaurantJpaRepository restaurantJpaRepository,
    RestaurantDataAccessMapper restaurantDataAccessMapper
  ) {
    this.restaurantJpaRepository = restaurantJpaRepository;
    this.restaurantDataAccessMapper = restaurantDataAccessMapper;
  }

  @Override
  public Optional<Restaurant> findRestaurantInformation (Restaurant restaurant) {
    List<UUID> restaurantProducts = restaurantDataAccessMapper.restaurantToRestaurantProducts(restaurant);

    Optional<List<RestaurantEntity>> restaurantEntities = restaurantJpaRepository.findByRestaurantIdAndProductIdIn(restaurant.getId().getValue(), restaurantProducts);

    return restaurantEntities.map(restaurantDataAccessMapper::restaurantProductsToRestaurant);
  }
}
