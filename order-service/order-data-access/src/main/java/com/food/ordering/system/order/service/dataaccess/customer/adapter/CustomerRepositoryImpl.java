package com.food.ordering.system.order.service.dataaccess.customer.adapter;


import java.util.*;
import org.springframework.stereotype.*;
import com.food.ordering.system.order.service.dataaccess.customer.entity.CustomerEntity;
import com.food.ordering.system.order.service.dataaccess.customer.mapper.CustomerDataAccessMapper;
import com.food.ordering.system.order.service.dataaccess.customer.repository.CustomerJPARepository;
import com.food.ordering.system.order.service.domain.entity.Customer;
import com.food.ordering.system.service.domain.ports.output.repository.CustomerRepository;

/**
 * @author juliwolf
 */

@Component
public class CustomerRepositoryImpl implements CustomerRepository {
  private final CustomerJPARepository customerJPARepository;
  private final CustomerDataAccessMapper customerDataAccessMapper;

  public CustomerRepositoryImpl (
    CustomerJPARepository customerJPARepository,
    CustomerDataAccessMapper customerDataAccessMapper
  ) {
    this.customerJPARepository = customerJPARepository;
    this.customerDataAccessMapper = customerDataAccessMapper;
  }

  @Override
  public Optional<Customer> findCustomer (UUID customerId) {
    return customerJPARepository.findById(customerId).map(customerDataAccessMapper::customerEntityToCustomer);
  }
}
