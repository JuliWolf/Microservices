package com.food.ordering.system.order.service.dataaccess.customer.mapper;


import org.springframework.stereotype.*;
import com.food.ordering.system.domain.valueObject.CustomerId;
import com.food.ordering.system.order.service.dataaccess.customer.entity.CustomerEntity;
import com.food.ordering.system.order.service.domain.entity.Customer;

/**
 * @author juliwolf
 */

@Component
public class CustomerDataAccessMapper {
  public Customer customerEntityToCustomer (CustomerEntity customerEntity) {
    return new Customer(new CustomerId(customerEntity.getCustomerId()));
  }
}
