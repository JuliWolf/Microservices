package com.food.ordering.system.payment.service.domain.valueObject;


import java.util.*;
import com.food.ordering.system.domain.valueObject.BaseId;

/**
 * @author juliwolf
 */

public class PaymentId extends BaseId<UUID> {
  public PaymentId (UUID value) {
    super(value);
  }
}
