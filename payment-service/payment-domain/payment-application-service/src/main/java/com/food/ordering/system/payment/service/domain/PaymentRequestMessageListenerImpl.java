package com.food.ordering.system.payment.service.domain;


import org.springframework.stereotype.*;
import com.food.ordering.system.payment.service.domain.dto.PaymentRequest;
import com.food.ordering.system.payment.service.domain.event.PaymentEvent;
import com.food.ordering.system.payment.service.domain.ports.input.message.listener.PaymentRequestMessageListener;
import com.food.ordering.system.payment.service.domain.ports.output.message.publisher.PaymentCancelledMessagePublisher;
import com.food.ordering.system.payment.service.domain.ports.output.message.publisher.PaymentCompletedMessagePublisher;
import com.food.ordering.system.payment.service.domain.ports.output.message.publisher.PaymentFailedMessagePublisher;
import lombok.extern.slf4j.Slf4j;

/**
 * @author juliwolf
 */

@Slf4j
@Service
public class PaymentRequestMessageListenerImpl implements PaymentRequestMessageListener {
  private final PaymentRequestHelper paymentRequestHelper;
  private final PaymentCompletedMessagePublisher paymentCompletedMessagePublisher;
  private final PaymentCancelledMessagePublisher paymentCancelledMessagePublisher;
  private final PaymentFailedMessagePublisher paymentFailedMessagePublisher;

  public PaymentRequestMessageListenerImpl (
    PaymentRequestHelper paymentRequestHelper,
    PaymentCompletedMessagePublisher paymentCompletedMessagePublisher,
    PaymentCancelledMessagePublisher paymentCancelledMessagePublisher,
    PaymentFailedMessagePublisher paymentFailedMessagePublisher
  ) {
    this.paymentRequestHelper = paymentRequestHelper;
    this.paymentCompletedMessagePublisher = paymentCompletedMessagePublisher;
    this.paymentCancelledMessagePublisher = paymentCancelledMessagePublisher;
    this.paymentFailedMessagePublisher = paymentFailedMessagePublisher;
  }

  @Override
  public void completePayment (PaymentRequest paymentRequest) {
    PaymentEvent paymentEvent = paymentRequestHelper.persistPayment(paymentRequest);
    fireEvent(paymentEvent);
  }

  @Override
  public void cancelPayment (PaymentRequest paymentRequest) {
    PaymentEvent paymentEvent = paymentRequestHelper.persistCancelPayment(paymentRequest);
    fireEvent(paymentEvent);
  }

  private void fireEvent (PaymentEvent paymentEvent) {
    log.info(
      "Publishing payment event with payment id: {} and order id: {}",
      paymentEvent.getPayment().getId().getValue(),
      paymentEvent.getPayment().getOrderId()
    );

    paymentEvent.fire();
  }
}
