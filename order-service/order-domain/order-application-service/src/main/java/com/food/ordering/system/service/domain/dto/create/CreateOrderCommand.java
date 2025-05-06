package com.food.ordering.system.service.domain.dto.create;


import java.math.BigDecimal;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * @author juliwolf
 */

@Getter
@Builder
@AllArgsConstructor
public class CreateOrderCommand {
  @NonNull
  private final UUID customerId;

  @NonNull
  private final UUID restaurantId;

  @NonNull
  private BigDecimal price;

  @NonNull
  private final List<OrderItem> items;

  @NonNull
  private final OrderAddress address;
}
