package com.food.ordering.system.payment.service.domain.valueObject;


import java.util.*;
import com.food.ordering.system.domain.valueObject.BaseId;

/**
 * @author juliwolf
 */

public class CreditHistoryId extends BaseId<UUID> {
  public CreditHistoryId (UUID value) {
    super(value);
  }
}
