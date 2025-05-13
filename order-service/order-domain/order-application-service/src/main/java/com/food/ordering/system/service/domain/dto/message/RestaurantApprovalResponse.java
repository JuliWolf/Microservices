package com.food.ordering.system.service.domain.dto.message;


import java.time.Instant;
import java.util.*;
import com.food.ordering.system.domain.valueObject.OrderApprovalStatus;
import com.food.ordering.system.domain.valueObject.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author juliwolf
 */

@Getter
@Builder
@AllArgsConstructor
public class RestaurantApprovalResponse {
  private UUID id;

  private UUID sagaId;

  private UUID orderId;

  private UUID restaurantId;

  private Instant createdAt;

  private OrderApprovalStatus orderApprovalStatus;

  private List<String> failureMessages;
}
