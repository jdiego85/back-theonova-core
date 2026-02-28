package com.theonova.response.catalog;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponse {
        Long parentId;
        String name;
        String slug;
}
