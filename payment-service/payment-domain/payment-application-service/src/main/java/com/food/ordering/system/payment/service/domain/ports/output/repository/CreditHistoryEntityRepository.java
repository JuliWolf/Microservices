package com.food.ordering.system.payment.service.domain.ports.output.repository;


import java.util.*;
import com.food.ordering.system.domain.valueObject.CustomerId;
import com.food.ordering.system.payment.service.domain.entity.CreditHistoryEntity;

/**
 * @author juliwolf
 */

public interface CreditHistoryEntityRepository {
  CreditHistoryEntity save(CreditHistoryEntity creditHistoryEntity);

  Optional<List<CreditHistoryEntity>> findByCustomerId(CustomerId customerId);
}
