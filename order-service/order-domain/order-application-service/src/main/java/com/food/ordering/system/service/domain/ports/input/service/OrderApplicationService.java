package com.food.ordering.system.service.domain.ports.input.service;


import jakarta.validation.Valid;
import com.food.ordering.system.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.service.domain.dto.track.TrackOrderQuery;
import com.food.ordering.system.service.domain.dto.track.TrackOrderResponse;

/**
 * @author juliwolf
 */

public interface OrderApplicationService {
  CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);

  TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
}
