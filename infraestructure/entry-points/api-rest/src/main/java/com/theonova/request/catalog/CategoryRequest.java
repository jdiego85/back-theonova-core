package com.theonova.request.catalog;

import com.theonova.exceptions.dto.CommonDataEntry;
import lombok.Builder;

@Builder
public class CategoryRequest extends CommonDataEntry {
        Long parentId;
        String name;
        String slug;
}
