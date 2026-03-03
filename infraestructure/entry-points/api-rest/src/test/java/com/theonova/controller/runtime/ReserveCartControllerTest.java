package com.theonova.controller.runtime;

import com.theonova.business.runtime.ReserveCartUseCase;
import com.theonova.entities.runtime.ReserveCart;
import com.theonova.entities.runtime.ReservedCart;
import com.theonova.entities.runtime.ReservedItem;
import com.theonova.exceptions.GlobalExceptionHandler;
import com.theonova.mappers.runtime.ReserveCartMapper;
import com.theonova.response.ApiResponseWrapper;
import com.theonova.response.runtime.ReserveCartResponse;
import com.theonova.response.runtime.ReservedItemResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

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
class ReserveCartControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReserveCartUseCase reserveCartUseCase;

    @Mock
    private ReserveCartMapper reserveCartMapper;

    @BeforeEach
    void setUp() {
        ReserveCartController controller = new ReserveCartController(reserveCartUseCase, reserveCartMapper);
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .build();
    }

    @Test
    void createShouldReturnOkWhenRequestIsValid() throws Exception {
        ReserveCart toDomain = ReserveCart.builder()
                .skuProduct("SKU-100")
                .codeWarehouse("WH-01")
                .userId(99L)
                .build();

        ReservedCart response = ReservedCart.builder()
                .reservationStatus("RESERVED")
                .reservedItems(List.of(
                        ReservedItem.builder()
                                .skuProduct("SKU-100")
                                .codeWarehouse("WH-01")
                                .reservedQuantity(2)
                                .build()
                ))
                .expiresAt(LocalDateTime.of(2026, 3, 1, 8, 30))
                .build();

        ApiResponseWrapper<ReserveCartResponse> wrapper = ApiResponseWrapper.wrapUp(
                "serviceReserveCart",
                ReserveCartResponse.builder()
                        .reservationStatus("RESERVED")
                        .reservedItems(List.of(
                                ReservedItemResponse.builder()
                                        .skuProduct("SKU-100")
                                        .codeWarehouse("WH-01")
                                        .reservedQuantity(2)
                                        .build()
                        ))
                        .expiresAt(LocalDateTime.of(2026, 3, 1, 8, 30))
                        .build()
        );

        when(reserveCartMapper.requestToDomain(any())).thenReturn(toDomain);
        when(reserveCartUseCase.execute(toDomain)).thenReturn(response);
        when(reserveCartMapper.toResponseWrapper(response)).thenReturn(wrapper);

        mockMvc.perform(post("/api/v1/reservation/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "skuProduct": "SKU-100",
                                  "codeWarehouse": "WH-01",
                                  "userId": 99
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service").value("serviceReserveCart"))
                .andExpect(jsonPath("$.data.reservationStatus").value("RESERVED"))
                .andExpect(jsonPath("$.data.reservedItems[0].skuProduct").value("SKU-100"))
                .andExpect(jsonPath("$.data.reservedItems[0].codeWarehouse").value("WH-01"))
                .andExpect(jsonPath("$.data.reservedItems[0].reservedQuantity").value(2));

        verify(reserveCartMapper).requestToDomain(any());
        verify(reserveCartUseCase).execute(toDomain);
        verify(reserveCartMapper).toResponseWrapper(response);
    }

    @Test
    void createShouldReturnBadRequestWhenValidationFails() throws Exception {
        mockMvc.perform(post("/api/v1/reservation/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "skuProduct": "",
                                  "codeWarehouse": "",
                                  "userId": 0
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.response").value("Error de validación"));

        verifyNoInteractions(reserveCartUseCase);
        verify(reserveCartMapper, never()).requestToDomain(any());
    }
}
