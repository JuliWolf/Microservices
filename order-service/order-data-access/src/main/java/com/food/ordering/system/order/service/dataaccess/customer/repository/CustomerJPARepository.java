package com.food.ordering.system.order.service.dataaccess.customer.repository;


import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import com.food.ordering.system.order.service.dataaccess.customer.entity.CustomerEntity;

/**
 * @author juliwolf
 */

@Repository
public interface CustomerJPARepository extends JpaRepository<CustomerEntity, UUID> {
}
