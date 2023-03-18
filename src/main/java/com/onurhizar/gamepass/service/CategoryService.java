package com.onurhizar.gamepass.service;

import com.onurhizar.gamepass.entity.Category;
import com.onurhizar.gamepass.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public Category addCategory(Category category){
        return repository.save(category);
    }

    public List<Category> listCategories(){
        return repository.findAll();
    }

    public Category singleCategory(String categoryId){
        return repository.findById(categoryId).orElseThrow(); // TODO exception handling
    }
}
