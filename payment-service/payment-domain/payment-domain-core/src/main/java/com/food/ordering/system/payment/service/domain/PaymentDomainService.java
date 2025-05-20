package com.food.ordering.system.payment.service.domain;


import java.util.*;
import com.food.ordering.system.payment.service.domain.entity.CreditEntity;
import com.food.ordering.system.payment.service.domain.entity.CreditHistoryEntity;
import com.food.ordering.system.payment.service.domain.entity.Payment;
import com.food.ordering.system.payment.service.domain.event.PaymentEvent;

/**
 * @author juliwolf
 */

public interface PaymentDomainService {
  PaymentEvent validateAndInitiatePayment (
    Payment payment,
    CreditEntity creditEntity,
    List<CreditHistoryEntity> creditHistories,
    List<String> failureMessages
  );

  PaymentEvent validateAndCancelPayment (
    Payment payment,
    CreditEntity creditEntity,
    List<CreditHistoryEntity> creditHistories,
    List<String> failureMessages
  );
}
