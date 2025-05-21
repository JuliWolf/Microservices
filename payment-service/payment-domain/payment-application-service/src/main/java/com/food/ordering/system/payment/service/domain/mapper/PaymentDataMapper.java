package com.food.ordering.system.payment.service.domain.mapper;


import java.util.*;
import org.springframework.stereotype.*;
import com.food.ordering.system.domain.valueObject.CustomerId;
import com.food.ordering.system.domain.valueObject.Money;
import com.food.ordering.system.domain.valueObject.OrderId;
import com.food.ordering.system.payment.service.domain.dto.PaymentRequest;
import com.food.ordering.system.payment.service.domain.entity.Payment;

/**
 * @author juliwolf
 */

@Component
public class PaymentDataMapper {
  public Payment paymentRequestModelToPayment (PaymentRequest paymentRequest) {
    return Payment.Builder.builder()
      .orderId(new OrderId(UUID.fromString(paymentRequest.getOrderId())))
      .customerId(new CustomerId(UUID.fromString(paymentRequest.getCustomerId())))
      .price(new Money(paymentRequest.getPrice()))
      .build();
  }
}
