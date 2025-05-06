package com.food.ordering.system.service.domain.dto.create;


import java.util.*;
import com.food.ordering.system.domain.valueObject.OrderStatus;
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
public class CreateOrderResponse {
  @NonNull
  private final UUID orderTrackingId;

  @NonNull
  private final OrderStatus orderStatus;

  @NonNull
  private final String message;
}
