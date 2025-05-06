package com.food.ordering.system.service.domain;


import org.springframework.stereotype.*;
import org.springframework.transaction.event.*;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import lombok.extern.slf4j.Slf4j;

/**
 * @author juliwolf
 */

@Slf4j
@Component
public class OrderCreatedEventApplicationListener {
  private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;

  public OrderCreatedEventApplicationListener (
    OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher
  ) {
    this.orderCreatedPaymentRequestMessagePublisher = orderCreatedPaymentRequestMessagePublisher;
  }

  @TransactionalEventListener
  void process(OrderCreatedEvent orderCreatedEvent) {
    orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);
  }
}
