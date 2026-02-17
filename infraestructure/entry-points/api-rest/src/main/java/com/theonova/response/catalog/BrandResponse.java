package com.theonova.response.catalog;

import com.theonova.exceptions.dto.CommonDataOutput;
import lombok.Builder;

@Builder
public class BrandResponse extends CommonDataOutput {
        String name;
}
