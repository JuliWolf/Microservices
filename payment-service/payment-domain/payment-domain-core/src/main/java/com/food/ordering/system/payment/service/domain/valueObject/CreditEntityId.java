package com.food.ordering.system.payment.service.domain.valueObject;


import java.util.*;
import com.food.ordering.system.domain.valueObject.BaseId;

/**
 * @author juliwolf
 */

public class CreditEntityId extends BaseId<UUID> {
  public CreditEntityId (UUID value) {
    super(value);
  }
}
