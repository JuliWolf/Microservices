package com.food.ordering.system.order.service.dataaccess.order.repository;


import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import com.food.ordering.system.order.service.dataaccess.order.entity.OrderEntity;

/**
 * @author juliwolf
 */

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {
  Optional<OrderEntity> findByTrackingId(UUID trackingId);
}
