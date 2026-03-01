package com.theonova.controller.inventory;

import com.theonova.business.inventory.InventoryBalanceUseCase;
import com.theonova.entities.inventory.InventoryBalance;
import com.theonova.exceptions.GlobalExceptionHandler;
import com.theonova.mappers.inventory.InventoryBalanceMapper;
import com.theonova.response.catalog.ApiResponseWrapper;
import com.theonova.response.inventory.InventoryBalanceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class InventoryBalanceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private InventoryBalanceUseCase inventoryBalanceUseCase;

    @Mock
    private InventoryBalanceMapper inventoryBalanceMapper;

    @BeforeEach
    void setUp() {
        InventoryBalanceController controller = new InventoryBalanceController(inventoryBalanceUseCase, inventoryBalanceMapper);
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .build();
    }

    @Test
    void createShouldReturnOkWhenRequestIsValid() throws Exception {
        InventoryBalance inventoryBalanceToDomain = InventoryBalance.builder()
                .skuProduct("SKU-001")
                .codeWarehouse("WH-01")
                .onHand(10)
                .reserved(2)
                .build();
        InventoryBalance inventoryBalanceResponse = inventoryBalanceToDomain.toBuilder().id(1L).build();
        ApiResponseWrapper<InventoryBalanceResponse> responseWrapper = ApiResponseWrapper.wrapUp(
                "serviceInventoryBalance",
                InventoryBalanceResponse.builder()
                        .skuProduct("SKU-001")
                        .codeWarehouse("WH-01")
                        .onHand(10)
                        .reserved(2)
                        .build()
        );

        when(inventoryBalanceMapper.requestToDomain(any())).thenReturn(inventoryBalanceToDomain);
        when(inventoryBalanceUseCase.execute(inventoryBalanceToDomain)).thenReturn(inventoryBalanceResponse);
        when(inventoryBalanceMapper.toResponseWrapper(inventoryBalanceResponse)).thenReturn(responseWrapper);

        mockMvc.perform(post("/api/v1/inventory-balance/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "skuProduct": "SKU-001",
                                  "codeWarehouse": "WH-01",
                                  "onHand": 10,
                                  "reserved": 2
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service").value("serviceInventoryBalance"))
                .andExpect(jsonPath("$.data.skuProduct").value("SKU-001"))
                .andExpect(jsonPath("$.data.codeWarehouse").value("WH-01"))
                .andExpect(jsonPath("$.data.onHand").value(10))
                .andExpect(jsonPath("$.data.reserved").value(2));

        verify(inventoryBalanceMapper).requestToDomain(any());
        verify(inventoryBalanceUseCase).execute(inventoryBalanceToDomain);
        verify(inventoryBalanceMapper).toResponseWrapper(inventoryBalanceResponse);
    }

    @Test
    void createShouldReturnBadRequestWhenValidationFails() throws Exception {
        mockMvc.perform(post("/api/v1/inventory-balance/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "skuProduct": "",
                                  "codeWarehouse": "WH-01",
                                  "onHand": -1,
                                  "reserved": 0
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("GER-001"))
                .andExpect(jsonPath("$.response").value("Error de validación"))
                .andExpect(jsonPath("$.description", anyOf(
                        containsString("skuProduct"),
                        containsString("onHand")
                )));

        verifyNoInteractions(inventoryBalanceUseCase);
        verify(inventoryBalanceMapper, never()).requestToDomain(any());
    }
}
