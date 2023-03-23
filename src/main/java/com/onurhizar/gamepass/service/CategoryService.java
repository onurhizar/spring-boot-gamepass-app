package com.onurhizar.gamepass.service;

import com.onurhizar.gamepass.model.entity.Category;
import com.onurhizar.gamepass.model.response.CategoryResponse;
import com.onurhizar.gamepass.model.request.PostCategoryRequest;
import com.onurhizar.gamepass.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    /**
     * Only adds child category for now, not super parent
     */
    public CategoryResponse addCategory(PostCategoryRequest request){
        // check parent if exists
        Category parent = repository.findCategoryByName(request.getParentName()).orElseThrow(); // TODO exception
        Category category = Category.builder()
                .name(request.getName())
                .isSuperCategory(false)
                .parent(parent)
                .build();
        return CategoryResponse.fromEntity(repository.save(category));
    }

    public List<CategoryResponse> listCategories(){
        return repository.findAll().stream()
                .map(CategoryResponse::fromEntity).toList();
    }

    public CategoryResponse singleCategory(String categoryId){
        Category category = repository.findById(categoryId).orElseThrow(); // TODO exception handling
        return CategoryResponse.fromEntity(category);
    }

    public CategoryResponse updateCategory(String id, PostCategoryRequest request) {
        Category category = repository.findById(id).orElseThrow(); // TODO exception handling
        Category parentCategory = repository.findCategoryByName(request.getParentName()).orElseThrow();

        if (category.getId().equals(parentCategory.getId()))
            throw new RuntimeException("a category cannot be its parent, same name is disallowed"); // TODO name

        category.setName(request.getName());
        category.setParent(parentCategory);
        return CategoryResponse.fromEntity(repository.save(category));
    }


    public void deleteCategory(String id) {
        Category category = repository.findById(id).orElseThrow();
        if (category.isSuperCategory())
            throw new RuntimeException("Super category cannot be deleted");
        repository.deleteById(id);
    }
}
