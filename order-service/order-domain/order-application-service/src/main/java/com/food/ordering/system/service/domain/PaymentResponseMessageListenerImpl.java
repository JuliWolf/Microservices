package com.food.ordering.system.service.domain;


import org.springframework.stereotype.*;
import org.springframework.validation.annotation.*;
import com.food.ordering.system.service.domain.dto.message.PaymentResponse;
import com.food.ordering.system.service.domain.ports.input.message.listener.payment.PaymentResponseMessageListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @author juliwolf
 */

@Slf4j
@Validated
@Service
public class PaymentResponseMessageListenerImpl implements PaymentResponseMessageListener {
  @Override
  public void paymentCompleted (PaymentResponse paymentResponse) {

  }

  @Override
  public void paymentCancelled (PaymentResponse paymentResponse) {

  }
}
