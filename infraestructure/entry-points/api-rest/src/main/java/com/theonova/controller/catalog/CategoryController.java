package com.theonova.controller.catalog;

import com.theonova.CategoryUseCase;
import com.theonova.entities.catalog.Category;
import com.theonova.mappers.catalog.CategoryMapper;
import com.theonova.request.catalog.CategoryRequest;
import com.theonova.response.catalog.ApiResponseWrapper;
import com.theonova.response.catalog.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryUseCase categoryUseCase;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseWrapper<CategoryResponse>> create (@RequestBody CategoryRequest categoryRequest) {
        Category categoryToDomain = CategoryMapper.requestToDomain(categoryRequest);
        Category categoryResponse = categoryUseCase.execute(categoryToDomain);
        return ResponseEntity.ok(CategoryMapper.domainToResponseWrapper(categoryResponse));
    }
}
