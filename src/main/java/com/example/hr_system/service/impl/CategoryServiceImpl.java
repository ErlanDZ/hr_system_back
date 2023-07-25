package com.example.hr_system.service.impl;

import com.example.hr_system.dto.category.CategoryRequest;
import com.example.hr_system.dto.category.CategoryResponse;
import com.example.hr_system.entities.Category;
import com.example.hr_system.repository.CategoryRepository;
import com.example.hr_system.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());

        categoryRepository.save(category);
        return new CategoryResponse(category.getId(),category.getName());
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category updateCategory(Long id, CategoryRequest categoryRequest) {
        Category category1 = categoryRepository.findById(id).get();

        category1.setName(categoryRequest.getName());
        return categoryRepository.save(category1);
    }

    public CategoryResponse convertEntityToResponseById(Category category){
        if (category == null) {
            return null;
        }
        CategoryResponse response = new CategoryResponse(category.getId(), category.getName());
        return response;
    }
}

