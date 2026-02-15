package com.theonova.response.catalog;

import lombok.Builder;

@Builder
public record CategoryResponse(
        Long parentId,
        String name,
        String slug
) {
}
