package com.food.ordering.system.service.domain;


import org.springframework.context.*;
import org.springframework.stereotype.*;
import com.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author juliwolf
 */

@Slf4j
@Component
public class ApplicationDomainEventPublisher implements ApplicationEventPublisherAware, DomainEventPublisher<OrderCreatedEvent> {
  private ApplicationEventPublisher applicationEventPublisher;

  @Override
  public void publish (OrderCreatedEvent domainEvent) {
    this.applicationEventPublisher.publishEvent(domainEvent);

    log.info("OrderCreatedEvent is published for order id: {}", domainEvent.getOrder().getId().getValue());
  }

  @Override
  public void setApplicationEventPublisher (ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }
}
