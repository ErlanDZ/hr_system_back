package com.example.hr_system.controller;

import com.example.hr_system.dto.category.CategoryRequest;
import com.example.hr_system.dto.category.CategoryResponse;
import com.example.hr_system.entities.Category;
import com.example.hr_system.service.impl.CategoryServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @GetMapping("/get/categories")
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryService.getAllCategory();
        return mapToCategoryResponses(categories);
    }

    @GetMapping("/get/category/{id}")
    public CategoryResponse getCategoryById(@PathVariable("id") Long id) {
        Optional<Category> optionalCategory = categoryService.getCategoryById(id);
        return optionalCategory.map(this::mapToCategoryResponse).orElse(null);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("delete/category/{id}")
    public void deleteCategory(@PathVariable("id") Long id){
        categoryService.deleteCategoryById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/update/category/{id}")
    public CategoryResponse updateCategory(@PathVariable("id") Long id, @RequestBody CategoryRequest categoryRequest) {
        Category updatedCategory = categoryService.updateCategory(id, categoryRequest);
        return mapToCategoryResponse(updatedCategory);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/create/category")
    public void createCategory(@RequestBody CategoryRequest categoryRequest){
        categoryService.createCategory(categoryRequest);
    }

    private CategoryResponse mapToCategoryResponse(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        return categoryResponse;
    }

    private List<CategoryResponse> mapToCategoryResponses(List<Category> categories) {
        return categories.stream()
                .map(this::mapToCategoryResponse)
                .collect(Collectors.toList());
    }
}
