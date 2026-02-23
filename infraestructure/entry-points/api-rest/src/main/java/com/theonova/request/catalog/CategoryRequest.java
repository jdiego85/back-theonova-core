package com.theonova.request.catalog;

import com.theonova.exceptions.dto.CommonDataEntry;
import lombok.Builder;
import lombok.Data;

@Data
public class CategoryRequest extends CommonDataEntry {
        Long parentId;
        String name;
        String slug;
}
