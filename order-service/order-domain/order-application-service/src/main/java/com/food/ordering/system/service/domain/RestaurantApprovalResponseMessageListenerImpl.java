package com.food.ordering.system.service.domain;


import org.springframework.stereotype.*;
import org.springframework.validation.annotation.*;
import com.food.ordering.system.service.domain.dto.message.RestaurantApprovalResponse;
import com.food.ordering.system.service.domain.ports.input.message.listener.restaurantApproval.RestaurantApprovalResponseMessageListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @author juliwolf
 */

@Slf4j
@Validated
@Service
public class RestaurantApprovalResponseMessageListenerImpl implements RestaurantApprovalResponseMessageListener {
  @Override
  public void orderApproved (RestaurantApprovalResponse restaurantApprovalResponse) {

  }

  @Override
  public void orderRejected (RestaurantApprovalResponse restaurantApprovalResponse) {

  }
}
