package com.food.ordering.system.payment.service.domain.ports.output.repository;


import java.util.*;
import com.food.ordering.system.domain.valueObject.CustomerId;
import com.food.ordering.system.payment.service.domain.entity.CreditHistory;

/**
 * @author juliwolf
 */

public interface CreditHistoryRepository {
  CreditHistory save(CreditHistory creditHistory);

  Optional<List<CreditHistory>> findByCustomerId(CustomerId customerId);
}
