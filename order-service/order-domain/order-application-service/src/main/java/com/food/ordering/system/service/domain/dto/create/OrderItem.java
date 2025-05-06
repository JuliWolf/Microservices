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
public class OrderItem {
  @NonNull
  private final UUID productId;

  @NonNull
  private final Integer quantity;

  @NonNull
  private final BigDecimal price;

  @NonNull
  private final BigDecimal subTotal;
}
