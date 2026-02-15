package com.theonova.request.catalog;

import lombok.Builder;

@Builder
public record CategoryRequest(
        Long parentId,
        String name,
        String slug
) {
}
