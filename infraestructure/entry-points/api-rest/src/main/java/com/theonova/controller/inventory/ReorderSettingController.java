package com.theonova.controller.inventory;

import com.theonova.business.inventory.ReorderSettingUseCase;
import com.theonova.entities.inventory.ReorderSetting;
import com.theonova.mappers.inventory.ReorderSettingMapper;
import com.theonova.request.inventory.ReorderSettingRequest;
import com.theonova.response.ApiResponseWrapper;
import com.theonova.response.inventory.ReorderSettingResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reorder-setting/")
@RequiredArgsConstructor
public class ReorderSettingController {

    private final ReorderSettingUseCase reorderSettingUseCase;
    private final ReorderSettingMapper mapper;

    @PostMapping("create")
    public ResponseEntity<ApiResponseWrapper<ReorderSettingResponse>> create(@Valid @RequestBody ReorderSettingRequest request) {
        ReorderSetting reorderSettingToDomain = mapper.requestToDomain(request);
        ReorderSetting response = reorderSettingUseCase.execute(reorderSettingToDomain);
        ApiResponseWrapper<ReorderSettingResponse> responseWrapper = mapper.toResponseWrapper(response);
        return ResponseEntity.ok(responseWrapper);
    }

}
