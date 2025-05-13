package com.food.ordering.system.order.service.dataaccess.restaurant.mapper;


import java.util.*;
import org.springframework.stereotype.*;
import com.food.ordering.system.domain.valueObject.Money;
import com.food.ordering.system.domain.valueObject.ProductId;
import com.food.ordering.system.domain.valueObject.RestaurantId;
import com.food.ordering.system.order.service.dataaccess.restaurant.entity.RestaurantEntity;
import com.food.ordering.system.order.service.dataaccess.restaurant.exception.RestaurantDataAccessException;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;

/**
 * @author juliwolf
 */

@Component
public class RestaurantDataAccessMapper {
  public List<UUID> restaurantToRestaurantProducts (Restaurant restaurant) {
    return restaurant.getProducts().stream()
      .map(product -> product.getId().getValue())
      .toList();
  }

  public Restaurant restaurantProductsToRestaurant (List<RestaurantEntity> restaurantEntities) {
    RestaurantEntity restaurantEntity = restaurantEntities.stream().findFirst().orElseThrow(() -> new RestaurantDataAccessException("Restaurant could not be found"));

    List<Product> restaurantProducts = restaurantEntities.stream()
      .map(entity -> new Product(
          new ProductId(entity.getProductId()),
          entity.getProductName(),
          new Money(entity.getProductPrice())
        )
      ).toList();

    return Restaurant.Builder.builder()
      .restaurantId(new RestaurantId(restaurantEntity.getRestaurantId()))
      .products(restaurantProducts)
      .isActive(restaurantEntity.getIsRestaurantActive())
      .build();
  }
}
