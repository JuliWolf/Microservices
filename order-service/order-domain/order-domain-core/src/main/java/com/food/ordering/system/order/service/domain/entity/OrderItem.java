package com.food.ordering.system.order.service.domain.entity;


import com.food.ordering.system.domain.entity.BaseEntity;
import com.food.ordering.system.domain.valueObject.Money;
import com.food.ordering.system.domain.valueObject.OrderId;
import com.food.ordering.system.order.service.domain.valueObject.OrderItemId;

/**
 * @author juliwolf
 */

public class OrderItem extends BaseEntity<OrderItemId> {
  private OrderId orderId;

  private final Product product;

  private final int quantity;

  private final Money price;

  private final Money subTotal;

  public OrderId getOrderId () {
    return orderId;
  }

  public Product getProduct () {
    return product;
  }

  public int getQuantity () {
    return quantity;
  }

  public Money getPrice () {
    return price;
  }

  public Money getSubTotal () {
    return subTotal;
  }

  private OrderItem (Builder builder) {
    super.setId(builder.orderItemId);

    orderId = builder.orderId;
    product = builder.product;
    quantity = builder.quantity;
    price = builder.price;
    subTotal = builder.subTotal;
  }

  void initializeOrderItem (OrderId orderId, OrderItemId orderItemId) {
    this.orderId = orderId;
    super.setId(orderItemId);
  }

  boolean isPriceValid () {
    return price.isGreaterThanZero() &&
      price.equals(product.getPrice()) &&
      price.multiply(quantity).equals(subTotal);
  }

  public static final class Builder {
    private OrderItemId orderItemId;
    private OrderId orderId;
    private Product product;
    private int quantity;
    private Money price;
    private Money subTotal;

    private Builder () {
    }

    public static Builder builder () {
      return new Builder();
    }

    public Builder orderItemId (OrderItemId val) {
      orderItemId = val;
      return this;
    }

    public Builder orderId (OrderId val) {
      orderId = val;
      return this;
    }

    public Builder product (Product val) {
      product = val;
      return this;
    }

    public Builder quantity (int val) {
      quantity = val;
      return this;
    }

    public Builder price (Money val) {
      price = val;
      return this;
    }

    public Builder subTotal (Money val) {
      subTotal = val;
      return this;
    }

    public OrderItem build () {
      return new OrderItem(this);
    }
  }
}
