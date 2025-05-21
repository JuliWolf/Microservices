package com.food.ordering.system.payment.service.domain.ports.output.repository;


import java.util.*;
import com.food.ordering.system.domain.valueObject.CustomerId;
import com.food.ordering.system.payment.service.domain.entity.CreditEntry;

/**
 * @author juliwolf
 */

public interface CreditEntryRepository {
  CreditEntry save(CreditEntry creditEntry);

  Optional<CreditEntry> findByCustomerId(CustomerId customerId);
}
