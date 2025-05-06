package com.food.ordering.system.service.domain;


import java.util.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.exception.OrderNotFoundException;
import com.food.ordering.system.order.service.domain.valueObject.TrackingId;
import com.food.ordering.system.service.domain.dto.track.TrackOrderQuery;
import com.food.ordering.system.service.domain.dto.track.TrackOrderResponse;
import com.food.ordering.system.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.service.domain.ports.output.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author juliwolf
 */

@Slf4j
@Component
public class OrderTrackCommandHandler {
  private final OrderDataMapper orderDataMapper;

   private final OrderRepository orderRepository;

  public OrderTrackCommandHandler (
    OrderDataMapper orderDataMapper,
    OrderRepository orderRepository
  ) {
    this.orderDataMapper = orderDataMapper;
    this.orderRepository = orderRepository;
  }

  @Transactional(readOnly = true)
  public TrackOrderResponse trackOrder (TrackOrderQuery trackOrderQuery) {
    Optional<Order> order = orderRepository.findByTrackingId(new TrackingId(trackOrderQuery.getOrderTrackingId()));

    if (order.isEmpty()) {
      log.warn("Could not find order with tracking id: {}", trackOrderQuery.getOrderTrackingId());

      throw new OrderNotFoundException("Could not find order with tracking id: " + trackOrderQuery.getOrderTrackingId());
    }

    return orderDataMapper.orderToTrackOrderResponse(order.get());
  }
}
