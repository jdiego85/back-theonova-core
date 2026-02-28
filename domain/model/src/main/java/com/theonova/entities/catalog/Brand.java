package com.theonova.entities.catalog;

import lombok.Builder;

import java.time.Instant;

@Builder
public record Brand(
        Long id,
        String name
) {}
