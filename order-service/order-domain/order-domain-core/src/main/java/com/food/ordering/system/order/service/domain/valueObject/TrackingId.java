package com.food.ordering.system.order.service.domain.valueObject;


import java.util.*;
import com.food.ordering.system.domain.valueObject.BaseId;

/**
 * @author juliwolf
 */

public class TrackingId extends BaseId<UUID> {
  public TrackingId (UUID value) {
    super(value);
  }
}
