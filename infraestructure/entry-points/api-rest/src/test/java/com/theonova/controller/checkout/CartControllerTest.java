package com.theonova.controller.checkout;

import com.theonova.business.checkout.CartUseCase;
import com.theonova.entities.checkout.Cart;
import com.theonova.enums.CartStatus;
import com.theonova.exceptions.GlobalExceptionHandler;
import com.theonova.mappers.checkout.CartMapper;
import com.theonova.response.catalog.ApiResponseWrapper;
import com.theonova.response.checkout.CartResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CartUseCase cartUseCase;

    @Mock
    private CartMapper cartMapper;

    @BeforeEach
    void setUp() {
        CartController controller = new CartController(cartUseCase, cartMapper);
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .build();
    }

    @Test
    void createCartShouldReturnOkWhenRequestIsValid() throws Exception {
        Instant now = Instant.parse("2026-03-01T10:00:00Z");
        Cart toDomain = new Cart(null, 77L, CartStatus.ACTIVE, now, null);
        Cart persisted = new Cart(1L, 77L, CartStatus.ACTIVE, now, null);
        ApiResponseWrapper<CartResponse> wrapper = ApiResponseWrapper.wrapUp(
                "serviceCart",
                CartResponse.builder()
                        .userId(77L)
                        .status(CartStatus.ACTIVE)
                        .lastActivityAt(now)
                        .build()
        );

        when(cartMapper.requestToDomain(any())).thenReturn(toDomain);
        when(cartUseCase.execute(toDomain)).thenReturn(persisted);
        when(cartMapper.toResponseWrapper(persisted)).thenReturn(wrapper);

        mockMvc.perform(post("/api/v1/cart/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": 77,
                                  "status": "ABANDONED"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service").value("serviceCart"))
                .andExpect(jsonPath("$.data.userId").value(77))
                .andExpect(jsonPath("$.data.status").value("ACTIVE"));

        verify(cartMapper).requestToDomain(any());
        verify(cartUseCase).execute(toDomain);
        verify(cartMapper).toResponseWrapper(persisted);
    }

    @Test
    void createCartShouldReturnBadRequestWhenUserIdIsInvalid() throws Exception {
        mockMvc.perform(post("/api/v1/cart/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": 0
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.response").value("Error de validación"))
                .andExpect(jsonPath("$.description").value("userId: userId must be greater than 0"));

        verifyNoInteractions(cartUseCase);
        verify(cartMapper, never()).requestToDomain(any());
    }
}
