package com.food.ordering.system.domain.valueObject;


import java.util.*;

/**
 * @author juliwolf
 */

public class OrderId extends BaseId<UUID> {
  public OrderId (UUID value) {
    super(value);
  }
}
