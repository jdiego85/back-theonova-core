package com.theonova.controller.checkout;

import com.theonova.business.checkout.CartItemUseCase;
import com.theonova.entities.checkout.CartItem;
import com.theonova.exceptions.GlobalExceptionHandler;
import com.theonova.mappers.checkout.CartItemMapper;
import com.theonova.response.ApiResponseWrapper;
import com.theonova.response.checkout.CartItemResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CartItemControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CartItemUseCase cartItemUseCase;

    @Mock
    private CartItemMapper cartItemMapper;

    @BeforeEach
    void setUp() {
        CartItemController controller = new CartItemController(cartItemUseCase, cartItemMapper);
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .build();
    }

    @Test
    void createCartItemsShouldReturnOkWhenRequestIsValid() throws Exception {
        CartItem toDomain = CartItem.builder()
                .userId(99L)
                .skuProduct("SKU-100")
                .codeWarehouse("WH-01")
                .quantity(2)
                .build();
        CartItem response = toDomain.toBuilder()
                .id(1L)
                .cartId(3L)
                .productId(10L)
                .warehouseId(20L)
                .build();

        ApiResponseWrapper<CartItemResponse> wrapper = ApiResponseWrapper.wrapUp(
                "serviceCartItem",
                CartItemResponse.builder()
                        .userId(99L)
                        .skuProduct("SKU-100")
                        .codeWarehouse("WH-01")
                        .quantity(2)
                        .build()
        );

        when(cartItemMapper.requestToDomain(any())).thenReturn(toDomain);
        when(cartItemUseCase.execute(toDomain)).thenReturn(response);
        when(cartItemMapper.toResponseWrapper(response)).thenReturn(wrapper);

        mockMvc.perform(post("/api/v1/cart-items/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "skuProduct": "SKU-100",
                                  "codeWarehouse": "WH-01",
                                  "userId": 99,
                                  "quantity": 2
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service").value("serviceCartItem"))
                .andExpect(jsonPath("$.data.userId").value(99))
                .andExpect(jsonPath("$.data.skuProduct").value("SKU-100"))
                .andExpect(jsonPath("$.data.codeWarehouse").value("WH-01"))
                .andExpect(jsonPath("$.data.quantity").value(2));

        verify(cartItemMapper).requestToDomain(any());
        verify(cartItemUseCase).execute(toDomain);
        verify(cartItemMapper).toResponseWrapper(response);
    }

    @Test
    void createCartItemsShouldReturnBadRequestWhenValidationFails() throws Exception {
        mockMvc.perform(post("/api/v1/cart-items/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "skuProduct": "",
                                  "codeWarehouse": "WH-01",
                                  "userId": 99,
                                  "quantity": 0
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.response").value("Error de validación"));

        verifyNoInteractions(cartItemUseCase);
        verify(cartItemMapper, never()).requestToDomain(any());
    }
}
