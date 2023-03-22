package com.onurhizar.gamepass.service;

import com.onurhizar.gamepass.entity.Category;
import com.onurhizar.gamepass.model.CategoryResponse;
import com.onurhizar.gamepass.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public Category addCategory(Category category){
        return repository.save(category);
    }

    public List<CategoryResponse> listCategories(){
        return repository.findAll().stream()
                .map(CategoryResponse::fromEntity).toList();
    }

    public CategoryResponse singleCategory(String categoryId){
        Category category = repository.findById(categoryId).orElseThrow(); // TODO exception handling
        return CategoryResponse.fromEntity(category);
    }
}
