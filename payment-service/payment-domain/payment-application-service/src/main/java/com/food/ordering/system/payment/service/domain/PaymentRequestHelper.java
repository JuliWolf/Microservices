package com.food.ordering.system.payment.service.domain;


import java.util.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import com.food.ordering.system.domain.valueObject.CustomerId;
import com.food.ordering.system.payment.service.domain.dto.PaymentRequest;
import com.food.ordering.system.payment.service.domain.entity.CreditEntry;
import com.food.ordering.system.payment.service.domain.entity.CreditHistory;
import com.food.ordering.system.payment.service.domain.entity.Payment;
import com.food.ordering.system.payment.service.domain.event.PaymentEvent;
import com.food.ordering.system.payment.service.domain.exception.PaymentApplicationServiceException;
import com.food.ordering.system.payment.service.domain.mapper.PaymentDataMapper;
import com.food.ordering.system.payment.service.domain.ports.output.message.publisher.PaymentCancelledMessagePublisher;
import com.food.ordering.system.payment.service.domain.ports.output.message.publisher.PaymentCompletedMessagePublisher;
import com.food.ordering.system.payment.service.domain.ports.output.message.publisher.PaymentFailedMessagePublisher;
import com.food.ordering.system.payment.service.domain.ports.output.repository.CreditEntryRepository;
import com.food.ordering.system.payment.service.domain.ports.output.repository.CreditHistoryRepository;
import com.food.ordering.system.payment.service.domain.ports.output.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author juliwolf
 */

@Slf4j
@Component
public class PaymentRequestHelper {
  private final PaymentDomainService paymentDomainService;
  private final PaymentDataMapper paymentDataMapper;
  private final PaymentRepository paymentRepository;
  private final CreditEntryRepository creditEntryRepository;
  private final CreditHistoryRepository creditHistoryRepository;
  private final PaymentCompletedMessagePublisher paymentCompletedMessagePublisher;
  private final PaymentFailedMessagePublisher paymentFailedMessagePublisher;
  private final PaymentCancelledMessagePublisher paymentCancelledMessagePublisher;

  public PaymentRequestHelper (
    PaymentDomainService paymentDomainService,
    PaymentDataMapper paymentDataMapper,
    PaymentRepository paymentRepository,
    CreditEntryRepository creditEntryRepository,
    CreditHistoryRepository creditHistoryRepository,
    PaymentCompletedMessagePublisher paymentCompletedMessagePublisher,
    PaymentFailedMessagePublisher paymentFailedMessagePublisher,
    PaymentCancelledMessagePublisher paymentCancelledMessagePublisher
  ) {
    this.paymentDomainService = paymentDomainService;
    this.paymentDataMapper = paymentDataMapper;
    this.paymentRepository = paymentRepository;
    this.creditEntryRepository = creditEntryRepository;
    this.creditHistoryRepository = creditHistoryRepository;
    this.paymentCompletedMessagePublisher = paymentCompletedMessagePublisher;
    this.paymentFailedMessagePublisher = paymentFailedMessagePublisher;
    this.paymentCancelledMessagePublisher = paymentCancelledMessagePublisher;
  }

  @Transactional
  public PaymentEvent persistPayment (PaymentRequest paymentRequest) {
    log.info("Received payment complete event for order id: {}", paymentRequest.getOrderId());

    Payment payment = paymentDataMapper.paymentRequestModelToPayment(paymentRequest);
    CreditEntry creditEntry = getCreditEntry(payment.getCustomerId());
    List<CreditHistory> creditHistoryEntities = getCreditHistory(payment.getCustomerId());
    List<String> failureMessages = new ArrayList<>();

    PaymentEvent paymentEvent = paymentDomainService.validateAndInitiatePayment(
      payment,
      creditEntry,
      creditHistoryEntities,
      failureMessages,
      paymentCompletedMessagePublisher,
      paymentFailedMessagePublisher
    );

    persistDBObjects(payment, creditEntry, creditHistoryEntities, failureMessages);

    return paymentEvent;
  }

  @Transactional
  public PaymentEvent persistCancelPayment (PaymentRequest paymentRequest) {
    log.info("Received payment rollback event for order id: {}", paymentRequest.getOrderId());

    Optional<Payment> paymentResponse = paymentRepository.findByOrderId(UUID.fromString(paymentRequest.getOrderId()));

    if (paymentResponse.isEmpty()) {
      log.error("Payment with order id: {} could not be found", paymentRequest.getOrderId());
      throw new PaymentApplicationServiceException("Payment with order id: " + paymentRequest.getOrderId() + " could not be found");
    }

    Payment payment = paymentResponse.get();
    CreditEntry creditEntry = getCreditEntry(payment.getCustomerId());
    List<CreditHistory> creditHistoryEntities = getCreditHistory(payment.getCustomerId());
    List<String> failureMessages = new ArrayList<>();

    PaymentEvent paymentEvent = paymentDomainService.validateAndCancelPayment(
      payment,
      creditEntry,
      creditHistoryEntities,
      failureMessages,
      paymentCancelledMessagePublisher,
      paymentFailedMessagePublisher
    );

    persistDBObjects(payment, creditEntry, creditHistoryEntities, failureMessages);

    return paymentEvent;
  }

  private void persistDBObjects (
    Payment payment,
    CreditEntry creditEntry,
    List<CreditHistory> creditHistoryEntities,
    List<String> failureMessages
  ) {
    paymentRepository.save(payment);

    if (failureMessages.isEmpty()) {
      creditEntryRepository.save(creditEntry);
      creditHistoryRepository.save(creditHistoryEntities.get(creditHistoryEntities.size() - 1));
    }
  }

  private CreditEntry getCreditEntry (CustomerId customerId) {
    Optional<CreditEntry> creditEntity = creditEntryRepository.findByCustomerId(customerId);

    if (creditEntity.isEmpty()) {
      log.error("Could not find credit entry for customer id: {}", customerId.getValue());
      throw new PaymentApplicationServiceException("Could not find credit entry for customer id: " + customerId.getValue());
    }

    return creditEntity.get();
  }

  private List<CreditHistory> getCreditHistory (CustomerId customerId) {
    Optional<List<CreditHistory>> creditHistoryEntities = creditHistoryRepository.findByCustomerId(customerId);

    if (creditHistoryEntities.isEmpty()) {
      log.error("Could not find credit history for customer id: {}", customerId.getValue());
      throw new PaymentApplicationServiceException("Could not find credit history for customer id: " + customerId);
    }

    return creditHistoryEntities.get();
  }
}
