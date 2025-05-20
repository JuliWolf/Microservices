package com.food.ordering.system.payment.service.domain.ports.output.repository;


import java.util.*;
import com.food.ordering.system.payment.service.domain.entity.Payment;

/**
 * @author juliwolf
 */

public interface PaymentRepository {
  Payment save(Payment payment);

  Optional<Payment> findByOrderId(UUID orderId);
}
