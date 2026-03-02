package com.theonova.request.admin;

import com.theonova.exceptions.dto.CommonDataEntry;
import lombok.Data;

@Data
public class CategoryRequest extends CommonDataEntry {
        Long parentId;
        String name;
        String slug;
}
