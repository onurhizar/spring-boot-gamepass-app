package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.entity.Category;
import com.onurhizar.gamepass.model.CategoryResponse;
import com.onurhizar.gamepass.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponse> listCategories(){
        return categoryService.listCategories();
    }

    @GetMapping("/{id}")
    public CategoryResponse singleCategory(@PathVariable String id){
        return categoryService.singleCategory(id);
    }
}
