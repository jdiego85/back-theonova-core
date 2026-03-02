package com.theonova.response.admin;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponse {
        Long parentId;
        String name;
        String slug;
}
