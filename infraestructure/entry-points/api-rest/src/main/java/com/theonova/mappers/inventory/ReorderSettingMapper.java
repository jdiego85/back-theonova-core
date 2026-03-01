package com.theonova.mappers.inventory;

import com.theonova.entities.inventory.ReorderSetting;
import com.theonova.request.inventory.ReorderSettingRequest;
import com.theonova.response.catalog.ApiResponseWrapper;
import com.theonova.response.inventory.ReorderSettingResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReorderSettingMapper {
    ReorderSetting requestToDomain(ReorderSettingRequest reorderSettingRequest);
    ReorderSettingResponse domainToResponse(ReorderSetting reorderSetting);

    default ApiResponseWrapper<ReorderSettingResponse> toResponseWrapper(ReorderSetting reorderSetting) {
        return ApiResponseWrapper.wrapUp("serviceReorderSetting", domainToResponse(reorderSetting));
    }
}
