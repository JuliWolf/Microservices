package com.food.ordering.system.service.domain.ports.input.message.listener.restaurantApproval;


import jakarta.validation.Valid;
import com.food.ordering.system.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.service.domain.dto.message.RestaurantApprovalResponse;

/**
 * @author juliwolf
 */

public interface RestaurantApprovalResponseMessageListener {
  void orderApproved (RestaurantApprovalResponse restaurantApprovalResponse);

  void orderRejected (RestaurantApprovalResponse restaurantApprovalResponse);
}
