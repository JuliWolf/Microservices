package com.food.ordering.system.payment.service.domain.entity;


import com.food.ordering.system.domain.entity.BaseEntity;
import com.food.ordering.system.domain.valueObject.CustomerId;
import com.food.ordering.system.domain.valueObject.Money;
import com.food.ordering.system.payment.service.domain.valueObject.CreditEntryId;

/**
 * @author juliwolf
 */

public class CreditEntry extends BaseEntity<CreditEntryId> {
  private final CustomerId customerId;
  private Money totalCreditAmount;

  private CreditEntry (Builder builder) {
   setId(builder.creditEntryId);
    customerId = builder.customerId;
    totalCreditAmount = builder.totalCreditAmount;
  }

  public CustomerId getCustomerId () {
    return customerId;
  }

  public Money getTotalCreditAmount () {
    return totalCreditAmount;
  }

  public void addCreditAmount (Money amount) {
    totalCreditAmount = totalCreditAmount.add(amount);
  }

  public void subtractCreditAmount (Money amount) {
    totalCreditAmount = totalCreditAmount.substract(amount);
  }

  public static final class Builder {
    private CreditEntryId creditEntryId;
    private CustomerId customerId;
    private Money totalCreditAmount;

    private Builder () {
    }

    public static Builder builder () {
      return new Builder();
    }

    public Builder creditEntryId (CreditEntryId val) {
      creditEntryId = val;
      return this;
    }

    public Builder customerId (CustomerId val) {
      customerId = val;
      return this;
    }

    public Builder totalCreditAmount (Money val) {
      totalCreditAmount = val;
      return this;
    }

    public CreditEntry build () {
      return new CreditEntry(this);
    }
  }
}
