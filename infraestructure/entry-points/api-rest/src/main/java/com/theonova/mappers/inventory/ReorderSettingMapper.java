package com.theonova.mappers.inventory;

import com.theonova.entities.inventory.ReorderSetting;
import com.theonova.request.admin.ReorderSettingRequest;
import com.theonova.response.ApiResponseWrapper;
import com.theonova.response.admin.ReorderSettingResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReorderSettingMapper {
    ReorderSetting requestToDomain(ReorderSettingRequest reorderSettingRequest);
    ReorderSettingResponse domainToResponse(ReorderSetting reorderSetting);

    default ApiResponseWrapper<ReorderSettingResponse> toResponseWrapper(ReorderSetting reorderSetting) {
        return ApiResponseWrapper.wrapUp("serviceReorderSetting", domainToResponse(reorderSetting));
    }
}
