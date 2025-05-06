package com.food.ordering.system.service.domain.ports.output.repository;


import java.util.*;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.valueObject.TrackingId;

/**
 * @author juliwolf
 */

public interface OrderRepository {
  Order save (Order order);

  Optional<Order> findByTrackingId(TrackingId trackingId);
}
