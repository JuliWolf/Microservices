package com.food.ordering.system.domain.valueObject;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @author juliwolf
 */

public class Money {
  private final BigDecimal amount;

  public static final Money ZERO = new Money(BigDecimal.ZERO);

  public BigDecimal getAmount () {
    return amount;
  }

  public Money (BigDecimal amount) {
    this.amount = amount;
  }

  public boolean isGreaterThanZero () {
    return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
  }

  public boolean isGreaterThan (Money money) {
    return amount != null && amount.compareTo(money.getAmount()) > 0;
  }

  public Money add (Money money) {
    return new Money(setScale(amount.add(money.getAmount())));
  }

  public Money substract(Money money) {
    return new Money(setScale(amount.subtract(money.getAmount())));
  }

  public Money multiply (int multiplier) {
    return new Money(setScale(amount.multiply(new BigDecimal(multiplier))));
  }

  @Override
  public boolean equals (Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Money money = (Money) o;
    return Objects.equals(amount, money.amount);
  }

  @Override
  public int hashCode () {
    return Objects.hashCode(amount);
  }

  private BigDecimal setScale (BigDecimal amount) {
    return amount.setScale(2, RoundingMode.HALF_EVEN);
  }
}
