package com.food.ordering.system.service.domain.ports.input.message.listener.payment;


import com.food.ordering.system.service.domain.dto.message.PaymentResponse;

/**
 * @author juliwolf
 */

public interface PaymentResponseMessageListener {
  void paymentCompleted (PaymentResponse paymentResponse);

  void paymentCancelled (PaymentResponse paymentResponse);
}
