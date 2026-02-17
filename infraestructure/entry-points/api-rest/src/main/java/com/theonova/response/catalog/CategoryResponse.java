package com.theonova.response.catalog;

import com.theonova.exceptions.dto.CommonDataOutput;
import lombok.Builder;

@Builder
public class CategoryResponse extends CommonDataOutput {
        Long parentId;
        String name;
        String slug;
}
