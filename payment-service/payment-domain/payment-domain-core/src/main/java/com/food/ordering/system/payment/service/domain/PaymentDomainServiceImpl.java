package com.food.ordering.system.payment.service.domain;


import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import com.food.ordering.system.domain.valueObject.Money;
import com.food.ordering.system.domain.valueObject.PaymentStatus;
import com.food.ordering.system.payment.service.domain.entity.CreditEntity;
import com.food.ordering.system.payment.service.domain.entity.CreditHistoryEntity;
import com.food.ordering.system.payment.service.domain.entity.Payment;
import com.food.ordering.system.payment.service.domain.event.PaymentCancelledEvent;
import com.food.ordering.system.payment.service.domain.event.PaymentCompletedEvent;
import com.food.ordering.system.payment.service.domain.event.PaymentEvent;
import com.food.ordering.system.payment.service.domain.event.PaymentFailedEvent;
import com.food.ordering.system.payment.service.domain.valueObject.CreditHistoryId;
import com.food.ordering.system.payment.service.domain.valueObject.TransactionType;
import lombok.extern.slf4j.Slf4j;

import static com.food.ordering.system.domain.DomainConstants.UTC;

/**
 * @author juliwolf
 */

@Slf4j
public class PaymentDomainServiceImpl implements PaymentDomainService {
  @Override
  public PaymentEvent validateAndInitiatePayment (
    Payment payment,
    CreditEntity creditEntity,
    List<CreditHistoryEntity> creditHistories,
    List<String> failureMessages
  ) {
    payment.validatePayment(failureMessages);
    payment.initializePayment();

    validateCreditEntry(payment, creditEntity, failureMessages);
    subtractCreditEntry(payment, creditEntity);
    updateCreditHistory(payment, creditHistories, TransactionType.DEBIT);
    validateCreditHistory(creditEntity, creditHistories, failureMessages);

    if (failureMessages.isEmpty()) {
      log.info("Payment is initiated for order id: {}", payment.getOrderId().getValue());
      payment.updateStatus(PaymentStatus.COMPLETED);

      return new PaymentCompletedEvent(payment, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    log.info("Payment initiation is failed for order id: {}", payment.getOrderId().getValue());
    payment.updateStatus(PaymentStatus.FAILED);
    return new PaymentFailedEvent(payment, ZonedDateTime.now(ZoneId.of(UTC)), failureMessages);
  }

  @Override
  public PaymentEvent validateAndCancelPayment (
    Payment payment,
    CreditEntity creditEntity,
    List<CreditHistoryEntity> creditHistories,
    List<String> failureMessages
  ) {
    payment.validatePayment(failureMessages);
    addCreditEntry(payment, creditEntity);
    updateCreditHistory(payment, creditHistories, TransactionType.CREDIT);

    if (failureMessages.isEmpty()) {
      log.info("Payment is cancelled fo order id: {}", payment.getOrderId().getValue());
      payment.updateStatus(PaymentStatus.CANCELLED);

      return new PaymentCancelledEvent(payment, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    log.info("Payment cancellation os failed for order id: {}", payment.getOrderId().getValue());
    payment.updateStatus(PaymentStatus.FAILED);
    return new PaymentFailedEvent(payment, ZonedDateTime.now(ZoneId.of(UTC)), failureMessages);
  }

  private void validateCreditEntry (
    Payment payment,
    CreditEntity creditEntity,
    List<String> failureMessages
  ) {
    if (payment.getPrice().isGreaterThan(creditEntity.getTotalCreditAmount())) {
      log.error("Customer with id: {} doesn't have enough credit for payment!", payment.getCustomerId().getValue());

      failureMessages.add("Customer with id=" + payment.getCustomerId().getValue() + " doesn't have enough credit for payment!");
    }
  }

  private void subtractCreditEntry (Payment payment, CreditEntity creditEntity) {
    creditEntity.subtractCreditAmount(payment.getPrice());
  }

  private void updateCreditHistory (
    Payment payment,
    List<CreditHistoryEntity> creditHistories,
    TransactionType transactionType
  ) {
    creditHistories.add(CreditHistoryEntity.Builder.builder()
      .creditHistoryId(new CreditHistoryId(UUID.randomUUID()))
      .customerId(payment.getCustomerId())
      .amount(payment.getPrice())
      .transactionType(transactionType)
      .build()
    );
  }

  private void validateCreditHistory (
    CreditEntity creditEntity,
    List<CreditHistoryEntity> creditHistories,
    List<String> failureMessages
  ) {
    Money totalCreditHistory = getTotalHistoryAmount(creditHistories, TransactionType.CREDIT);
    Money totalDebitHistory = getTotalHistoryAmount(creditHistories, TransactionType.DEBIT);

    if (totalDebitHistory.isGreaterThan(totalCreditHistory)) {
      log.error("Customer with id: {} doesn't have enough credit according to credit history", creditEntity.getCustomerId().getValue());

      failureMessages.add("Customer with id=" + creditEntity.getCustomerId().getValue() + " doesn't have enough credit according to credit history");
    }

    if (!creditEntity.getTotalCreditAmount().equals(totalCreditHistory.substract(totalDebitHistory))) {
      log.error("Credit history total is not equal to current credit for customer id: {}!", creditEntity.getCustomerId().getValue());

      failureMessages.add("Credit history total is not equal to current credit for customer id=" + creditEntity.getCustomerId().getValue());
    }
  }

  private Money getTotalHistoryAmount (
    List<CreditHistoryEntity> creditHistoryEntities,
    TransactionType transactionType
  ) {
    return creditHistoryEntities.stream()
      .filter(creditHistory -> transactionType == creditHistory.getTransactionType())
      .map(CreditHistoryEntity::getAmount)
      .reduce(Money.ZERO, Money::add);
  }

  private void addCreditEntry (Payment payment, CreditEntity creditEntity) {
    creditEntity.addCreditAmount(payment.getPrice());
  }
}
