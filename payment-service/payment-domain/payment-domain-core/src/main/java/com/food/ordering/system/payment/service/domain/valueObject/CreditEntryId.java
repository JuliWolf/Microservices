package com.food.ordering.system.payment.service.domain.valueObject;


import java.util.*;
import com.food.ordering.system.domain.valueObject.BaseId;

/**
 * @author juliwolf
 */

public class CreditEntryId extends BaseId<UUID> {
  public CreditEntryId (UUID value) {
    super(value);
  }
}
