package com.theonova.controller.checkout;

import com.theonova.business.checkout.OrderCheckoutUseCase;
import com.theonova.entities.checkout.OrderCheckout;
import com.theonova.entities.checkout.OrderCheckoutResult;
import com.theonova.entities.checkout.OrderCheckoutResultItem;
import com.theonova.exceptions.GlobalExceptionHandler;
import com.theonova.mappers.checkout.OrderCheckoutMapper;
import com.theonova.response.ApiResponseWrapper;
import com.theonova.response.checkout.OrderItemResponse;
import com.theonova.response.checkout.OrderResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrderCheckoutControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderCheckoutUseCase orderCheckoutUseCase;

    @Mock
    private OrderCheckoutMapper orderCheckoutMapper;

    @BeforeEach
    void setUp() {
        OrderCheckoutController controller = new OrderCheckoutController(orderCheckoutUseCase, orderCheckoutMapper);
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .build();
    }

    @Test
    void checkoutShouldReturnOkWhenRequestIsValid() throws Exception {
        OrderCheckout toDomain = OrderCheckout.builder()
                .userId(99L)
                .codeWarehouse("WH-01")
                .shippingName("Cliente")
                .shippingPhone("099")
                .shippingAddress("Quito")
                .currency("USD")
                .shipping(new BigDecimal("5.00"))
                .notes("Nota")
                .build();

        OrderCheckoutResult result = OrderCheckoutResult.builder()
                .orderId(101L)
                .orderNumber("EC-20260307-12345")
                .status("PENDING")
                .paymentStatus("PENDING")
                .subtotal(new BigDecimal("25.00"))
                .shipping(new BigDecimal("5.00"))
                .total(new BigDecimal("30.00"))
                .items(List.of(
                        OrderCheckoutResultItem.builder()
                                .skuProduct("SKU-100")
                                .productName("Producto 100")
                                .quantity(2)
                                .unitPrice(new BigDecimal("12.50"))
                                .lineTotal(new BigDecimal("25.00"))
                                .build()
                ))
                .createdAt(LocalDateTime.of(2026, 3, 7, 10, 30))
                .build();

        ApiResponseWrapper<OrderResponse> wrapper = ApiResponseWrapper.wrapUp(
                "serviceOrderCheckout",
                OrderResponse.builder()
                        .orderId(101L)
                        .orderNumber("EC-20260307-12345")
                        .status("PENDING")
                        .paymentStatus("PENDING")
                        .subtotal(new BigDecimal("25.00"))
                        .shipping(new BigDecimal("5.00"))
                        .total(new BigDecimal("30.00"))
                        .items(List.of(
                                OrderItemResponse.builder()
                                        .skuProduct("SKU-100")
                                        .productName("Producto 100")
                                        .quantity(2)
                                        .unitPrice(new BigDecimal("12.50"))
                                        .lineTotal(new BigDecimal("25.00"))
                                        .build()
                        ))
                        .createdAt(LocalDateTime.of(2026, 3, 7, 10, 30))
                        .build()
        );

        when(orderCheckoutMapper.requestToDomain(any())).thenReturn(toDomain);
        when(orderCheckoutUseCase.execute(toDomain)).thenReturn(result);
        when(orderCheckoutMapper.toResponseWrapper(result)).thenReturn(wrapper);

        mockMvc.perform(post("/api/v1/order-checkout/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "codeWarehouse": "WH-01",
                                  "userId": 99,
                                  "shippingName": "Cliente",
                                  "shippingPhone": "099",
                                  "shippingAddress": "Quito",
                                  "currency": "USD",
                                  "shipping": 5.00,
                                  "notes": "Nota"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service").value("serviceOrderCheckout"))
                .andExpect(jsonPath("$.data.orderId").value(101))
                .andExpect(jsonPath("$.data.status").value("PENDING"))
                .andExpect(jsonPath("$.data.items[0].skuProduct").value("SKU-100"));

        verify(orderCheckoutMapper).requestToDomain(any());
        verify(orderCheckoutUseCase).execute(toDomain);
        verify(orderCheckoutMapper).toResponseWrapper(result);
    }

    @Test
    void checkoutShouldReturnBadRequestWhenValidationFails() throws Exception {
        mockMvc.perform(post("/api/v1/order-checkout/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "codeWarehouse": "",
                                  "userId": 0,
                                  "shippingName": "",
                                  "shippingPhone": "",
                                  "shippingAddress": "",
                                  "currency": "US",
                                  "shipping": -1
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.response").value("Error de validación"));

        verifyNoInteractions(orderCheckoutUseCase);
        verify(orderCheckoutMapper, never()).requestToDomain(any());
    }
}
