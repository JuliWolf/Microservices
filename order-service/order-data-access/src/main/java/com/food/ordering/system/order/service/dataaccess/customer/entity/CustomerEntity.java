package com.food.ordering.system.order.service.dataaccess.customer.entity;


import java.util.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

/**
 * @author juliwolf
 */

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_customer_m_view", schema ="customers")
@Entity
public class CustomerEntity {
  @Id
  private UUID customerId;
}
