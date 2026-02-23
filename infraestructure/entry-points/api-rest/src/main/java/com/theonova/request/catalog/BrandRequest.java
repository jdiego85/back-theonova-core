package com.theonova.request.catalog;

import com.theonova.exceptions.dto.CommonDataEntry;
import lombok.Data;

@Data
public class BrandRequest extends CommonDataEntry {
        String name;
}
