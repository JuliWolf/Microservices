package com.food.ordering.system.order.service.dataaccess.order.adapter;


import java.util.*;
import org.springframework.stereotype.*;
import com.food.ordering.system.order.service.dataaccess.order.mapper.OrderDataAccessMapper;
import com.food.ordering.system.order.service.dataaccess.order.repository.OrderJpaRepository;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.valueObject.TrackingId;
import com.food.ordering.system.service.domain.ports.output.repository.OrderRepository;

/**
 * @author juliwolf
 */

@Component
public class OrderRepositoryImpl implements OrderRepository {
  private final OrderJpaRepository orderJpaRepository;
  private final OrderDataAccessMapper orderDataAccessMapper;

  public OrderRepositoryImpl (OrderJpaRepository orderJpaRepository, OrderDataAccessMapper orderDataAccessMapper) {
    this.orderJpaRepository = orderJpaRepository;
    this.orderDataAccessMapper = orderDataAccessMapper;
  }

  @Override
  public Order save (Order order) {
    return orderDataAccessMapper.orderEntityToOrder(
      orderJpaRepository.save(orderDataAccessMapper.orderToOrderEntity(order))
    );
  }

  @Override
  public Optional<Order> findByTrackingId (TrackingId trackingId) {
    return orderJpaRepository.findByTrackingId(trackingId.getValue())
      .map(orderDataAccessMapper::orderEntityToOrder);
  }
}
