package com.food.ordering.system.service.domain.dto.track;


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
public class TrackOrderResponse {
  @NonNull
  private final UUID orderTrackingId;

  @NonNull
  private final OrderStatus orderStatus;

  private final List<String> failureMessages;
}
