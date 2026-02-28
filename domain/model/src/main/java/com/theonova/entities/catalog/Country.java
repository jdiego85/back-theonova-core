package com.theonova.entities.catalog;

import lombok.Builder;

import java.time.Instant;

@Builder(toBuilder = true)
public record Country(
        Long id,
        String iso2,
        String name
) {}
