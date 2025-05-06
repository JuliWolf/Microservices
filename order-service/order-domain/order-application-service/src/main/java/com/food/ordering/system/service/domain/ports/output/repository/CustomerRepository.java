package com.food.ordering.system.service.domain.ports.output.repository;


import java.util.*;
import com.food.ordering.system.order.service.domain.entity.Customer;

/**
 * @author juliwolf
 */

public interface CustomerRepository {
  Optional<Customer> findCustomer(UUID customerId);
}
