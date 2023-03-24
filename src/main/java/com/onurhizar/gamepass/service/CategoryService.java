package com.onurhizar.gamepass.service;

import com.onurhizar.gamepass.exception.EntityNotFoundException;
import com.onurhizar.gamepass.model.entity.Category;
import com.onurhizar.gamepass.model.response.CategoryResponse;
import com.onurhizar.gamepass.model.request.UpdateCategoryRequest;
import com.onurhizar.gamepass.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository repository;

    /**
     * Only adds child category for now, not super parent
     */
    public CategoryResponse addCategory(String parentId, String name) {
        // check parent if exists
        Category parent = repository.findById(parentId)
                .orElseThrow(EntityNotFoundException::new);
        log.info(parent.toString());
        Category category = Category.builder()
                .name(name)
                .isSuperCategory(false)
                .parent(parent)
                .games(new HashSet<>()) // TODO default value is not HashSet? instead of null
                .build();
        return CategoryResponse.fromEntity(repository.save(category));
    }

    public List<CategoryResponse> listCategories(){
        return repository.findAll().stream()
                .map(CategoryResponse::fromEntity).toList();
    }

    public CategoryResponse singleCategory(String categoryId){
        Category category = repository.findById(categoryId)
                .orElseThrow(EntityNotFoundException::new);
        return CategoryResponse.fromEntity(category);
    }

    public CategoryResponse updateCategory(String id, UpdateCategoryRequest request) {
        Category category = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        Category parentCategory = repository.findCategoryByName(request.getParentName())
                .orElseThrow(EntityNotFoundException::new);

        if (category.getId().equals(parentCategory.getId()))
            throw new RuntimeException("a category cannot be its parent, same name is disallowed"); // TODO name

        category.setName(request.getName());
        category.setParent(parentCategory);
        return CategoryResponse.fromEntity(repository.save(category));
    }


    public void deleteCategory(String id) {
        Category category = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        if (category.isSuperCategory())
            throw new RuntimeException("Super category cannot be deleted");
        repository.deleteById(id);
    }
}
