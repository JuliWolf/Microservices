package com.food.ordering.system.order.service.domain;


import java.math.BigDecimal;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import com.food.ordering.system.domain.valueObject.*;
import com.food.ordering.system.order.service.domain.entity.Customer;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.service.domain.dto.create.OrderAddress;
import com.food.ordering.system.service.domain.dto.create.OrderItem;
import com.food.ordering.system.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.service.domain.ports.input.service.OrderApplicationService;
import com.food.ordering.system.service.domain.ports.output.repository.CustomerRepository;
import com.food.ordering.system.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.service.domain.ports.output.repository.RestaurantRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author juliwolf
 */

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = OrderTestConfiguration.class)
public class OrderApplicationServiceTest {
  @Autowired
  private OrderApplicationService orderApplicationService;

  @Autowired
  private OrderDataMapper orderDataMapper;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private RestaurantRepository restaurantRepository;

  @Autowired
  private CustomerRepository customerRepository;

  private CreateOrderCommand createOrderCommand;
  private CreateOrderCommand createOrderCommandWrongPrice;
  private CreateOrderCommand createOrderCommandWrongProductPrice;
  private final UUID CUSTOMER_ID = UUID.fromString("d215bf8-0249-4dc5-89a3-51fd148cfb41");
  private final UUID RESTAURANT_ID = UUID.fromString("d215bf8-0249-4dc5-89a3-51fd148cfb45");
  private final UUID PRODUCT_ID = UUID.fromString("d215bf8-0249-4dc5-89a3-51fd148cfb48");
  private final UUID ORDER_ID = UUID.fromString("15a497c1-0f4b-4eff-b9f4-c402c8c07afb");
  private final BigDecimal PRICE = new BigDecimal("200.00");

  @BeforeAll
  public void init () {
    createOrderCommand = CreateOrderCommand.builder()
      .customerId(CUSTOMER_ID)
      .restaurantId(RESTAURANT_ID)
      .address(OrderAddress.builder()
        .street("street_1")
        .postalCode("1000AB")
        .city("Paris")
        .build()
      )
      .price(PRICE)
      .items(List.of(
        OrderItem.builder()
        .productId(PRODUCT_ID)
        .quantity(1)
        .price(new BigDecimal("50.00"))
        .subTotal(new BigDecimal("50.00"))
        .build(),
        OrderItem.builder()
          .productId(PRODUCT_ID)
          .quantity(3)
          .price(new BigDecimal("50.00"))
          .subTotal(new BigDecimal("150.00"))
          .build()
      ))
      .build();

    createOrderCommandWrongPrice = CreateOrderCommand.builder()
      .customerId(CUSTOMER_ID)
      .restaurantId(RESTAURANT_ID)
      .address(OrderAddress.builder()
        .street("street_1")
        .postalCode("1000AB")
        .city("Paris")
        .build()
      )
      .price(new BigDecimal("250.00"))
      .items(List.of(
        OrderItem.builder()
          .productId(PRODUCT_ID)
          .quantity(1)
          .price(new BigDecimal("50.00"))
          .subTotal(new BigDecimal("50.00"))
          .build(),
        OrderItem.builder()
          .productId(PRODUCT_ID)
          .quantity(3)
          .price(new BigDecimal("50.00"))
          .subTotal(new BigDecimal("150.00"))
          .build()
      ))
      .build();

    createOrderCommandWrongProductPrice = CreateOrderCommand.builder()
      .customerId(CUSTOMER_ID)
      .restaurantId(RESTAURANT_ID)
      .address(OrderAddress.builder()
        .street("street_1")
        .postalCode("1000AB")
        .city("Paris")
        .build()
      )
      .price(new BigDecimal("210.00"))
      .items(List.of(
        OrderItem.builder()
          .productId(PRODUCT_ID)
          .quantity(1)
          .price(new BigDecimal("60.00"))
          .subTotal(new BigDecimal("60.00"))
          .build(),
        OrderItem.builder()
          .productId(PRODUCT_ID)
          .quantity(3)
          .price(new BigDecimal("50.00"))
          .subTotal(new BigDecimal("150.00"))
          .build()
      ))
      .build();

    Customer customer = new Customer();
    customer.setId(new CustomerId(CUSTOMER_ID));

    Restaurant restaurant = Restaurant.Builder.builder()
      .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
      .products(List.of(
        new Product(new ProductId(PRODUCT_ID), "product-1", new Money(new BigDecimal("50.00"))),
        new Product(new ProductId(PRODUCT_ID), "product-2", new Money(new BigDecimal("50.00")))
      ))
      .isActive(true)
      .build();

    Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
    order.setId(new OrderId(ORDER_ID));

    when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(customer));
    when(restaurantRepository.findRestaurantInformation(orderDataMapper.createOrderCommandTiRestaurant(createOrderCommand)))
      .thenReturn(Optional.of(restaurant));
    when(orderRepository.save(any(Order.class))).thenReturn(order);
  }

  @Test
  public void testCreateOrder () {
    CreateOrderResponse createOrderResponse = orderApplicationService.createOrder(createOrderCommand);

    assertAll(
      () -> assertEquals(OrderStatus.PENDING, createOrderResponse.getOrderStatus()),
      () -> assertEquals("Order Created Successfully", createOrderResponse.getMessage()),
      () -> assertNotNull(createOrderResponse.getOrderTrackingId())
    );
  }

  @Test
  public void testCreateOrderCommandWrongTotalPrice () {
    OrderDomainException orderDomainException = assertThrows(OrderDomainException.class, () -> orderApplicationService.createOrder(createOrderCommandWrongPrice));

    assertEquals("Total price: 250.00 is not equal to Order items price: 200.00", orderDomainException.getMessage());
  }

  @Test
  public void testCreateOrderCommandWrongProductPrice () {
    OrderDomainException orderDomainException = assertThrows(OrderDomainException.class, () -> orderApplicationService.createOrder(createOrderCommandWrongProductPrice));

    assertEquals("Order item price: 60.00 is not valid for product " + PRODUCT_ID, orderDomainException.getMessage());
  }

  @Test
  public void testCreateOrderWithPassiveRestaurant () {
    Restaurant restaurant = Restaurant.Builder.builder()
      .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
      .products(List.of(
        new Product(new ProductId(PRODUCT_ID), "product-1", new Money(new BigDecimal("50.00"))),
        new Product(new ProductId(PRODUCT_ID), "product-2", new Money(new BigDecimal("50.00")))
      ))
      .isActive(false)
      .build();

    when(restaurantRepository.findRestaurantInformation(orderDataMapper.createOrderCommandTiRestaurant(createOrderCommand)))
      .thenReturn(Optional.of(restaurant));

    OrderDomainException orderDomainException = assertThrows(OrderDomainException.class, () -> orderApplicationService.createOrder(createOrderCommand));

    assertEquals("Restaurant with id " + RESTAURANT_ID + " is currently not active", orderDomainException.getMessage());
  }
}
