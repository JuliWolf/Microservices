package com.food.ordering.system.payment.service.domain.ports.output.repository;


import java.util.*;
import com.food.ordering.system.domain.valueObject.CustomerId;
import com.food.ordering.system.payment.service.domain.entity.CreditEntity;

/**
 * @author juliwolf
 */

public interface CreditEntityRepository {
  CreditEntity save(CreditEntity creditEntity);

  Optional<CreditEntity> findByCustomerId(CustomerId customerId);
}
