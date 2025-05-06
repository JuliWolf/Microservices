package com.food.ordering.system.domain.valueObject;


import java.util.*;

/**
 * @author juliwolf
 */

public abstract class BaseId<T> {
  private final T value;

  public T getValue () {
    return value;
  }

  protected BaseId (T value) {
    this.value = value;
  }

  @Override
  public boolean equals (Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    BaseId<?> baseId = (BaseId<?>) o;
    return Objects.equals(value, baseId.value);
  }

  @Override
  public int hashCode () {
    return Objects.hashCode(value);
  }
}
