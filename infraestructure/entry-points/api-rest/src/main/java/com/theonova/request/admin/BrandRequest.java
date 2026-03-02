package com.theonova.request.admin;

import com.theonova.exceptions.dto.CommonDataEntry;
import lombok.Data;

@Data
public class BrandRequest extends CommonDataEntry {
        String name;
}
