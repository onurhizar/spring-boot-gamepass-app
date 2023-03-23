package com.onurhizar.gamepass.controller;


import com.onurhizar.gamepass.model.response.CategoryResponse;
import com.onurhizar.gamepass.model.request.PostCategoryRequest;
import com.onurhizar.gamepass.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public CategoryResponse addCategory(@RequestBody PostCategoryRequest request){
        return categoryService.addCategory(request);
    }

    @GetMapping("{id}")
    public CategoryResponse singleCategory(@PathVariable String id){
        return categoryService.singleCategory(id);
    }

    @PutMapping("{id}")
    public CategoryResponse updateCategory(@PathVariable String id, @RequestBody PostCategoryRequest request){
        return categoryService.updateCategory(id, request);
    }

    @DeleteMapping("{id}")
    public void deleteCategory(@PathVariable String id){
        categoryService.deleteCategory(id);
    }
}
