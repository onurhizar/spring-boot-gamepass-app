package com.onurhizar.gamepass.service;

import com.onurhizar.gamepass.exception.EntityNotFoundException;
import com.onurhizar.gamepass.exception.UnacceptableRequestException;
import com.onurhizar.gamepass.model.entity.Category;
import com.onurhizar.gamepass.model.entity.Game;
import com.onurhizar.gamepass.model.entity.User;
import com.onurhizar.gamepass.model.response.CategoryResponse;
import com.onurhizar.gamepass.model.request.UpdateCategoryRequest;
import com.onurhizar.gamepass.repository.CategoryRepository;
import com.onurhizar.gamepass.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository repository;
    private final UserRepository userRepository;

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
                .build();
        return CategoryResponse.fromEntity(repository.save(category));
    }

    public List<CategoryResponse> listCategories(){
        List<CategoryResponse> categoryResponses = new LinkedList<>();
        List<Category> categories = repository.findAll();
        for (Category category : categories) {
            appendAllGamesFromChildrenCategories(category.getId(), category.getGames());
            categoryResponses.add(CategoryResponse.fromEntity(category));
        }
        return categoryResponses;
    }

    public Category findCategoryById(String categoryId){
        Category category = repository.findById(categoryId)
                .orElseThrow(EntityNotFoundException::new);
        appendAllGamesFromChildrenCategories(categoryId, category.getGames());
        return category;
    }

    public CategoryResponse updateCategory(String id, UpdateCategoryRequest request) {
        Category category = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        Category parentCategory = repository.findCategoryByName(request.getParentName())
                .orElseThrow(EntityNotFoundException::new);

        if (category.getId().equals(parentCategory.getId()))
            throw new UnacceptableRequestException("a category cannot be its parent, same name is disallowed");

        category.setName(request.getName());
        category.setParent(parentCategory);
        return CategoryResponse.fromEntity(repository.save(category));
    }

    
    /** detach users first, then delete category */
    private void deleteCategory(String id) {
        Category category = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        if (category.isSuperCategory())
            throw new UnacceptableRequestException("Super category cannot be deleted");

        List<User> followingUsers = userRepository.findByFollowedCategories(category);
        for (User user : followingUsers) {
            user.getFollowedCategories().remove(category);
            userRepository.save(user);
        }

        repository.deleteById(id);
    }

    /** Returns a stack, the most leaf is at the top, root stays at the bottom to implement remove orphan logic */
    public Deque<Category> findAllChildrenCategories(String categoryId){
        Category rootCategory = repository.findById(categoryId)
                .orElseThrow(EntityNotFoundException::new);

        Deque<Category> deque = new LinkedList<>();
        traverseAllChildrenOfCategory(rootCategory, deque);

        // we need to reverse the stack to iterate from leaf to root
        Deque<Category> reversedStack = new LinkedList<>();
        while (!deque.isEmpty()) reversedStack.push(deque.pop());
        return reversedStack;
    }


    /** Returns reversed stack, starts from leaf and goes to root category */
    private Deque<Category> traverseAllChildrenOfCategory(Category rootCategory, Deque<Category> deque) {
        List<Category> childrenCategories = repository.findCategoriesByParentId(rootCategory.getId());

        if (!childrenCategories.isEmpty()) // first, iterate over children
            for (Category category : childrenCategories)
                traverseAllChildrenOfCategory(category, deque);

        deque.push(rootCategory); // then push self to stack
        return deque; // root node stays at the top of the stack, need to reverse it before using
    }
    
    public void deleteCategoryByAssignChildrenToGrandParent(String categoryId){
        Category category = findCategoryById(categoryId);
        Category parentCategory = category.getParent();
        List<Category> childrenCategories = repository.findCategoriesByParentId(categoryId); // find children
        for (Category childCategory : childrenCategories) {
            childCategory.setParent(parentCategory); // assign children to grandparent
            repository.save(childCategory);
        }
        deleteCategory(categoryId);
    }


    /** Appends all games of children categories' games, if same game exists in the list, does not append */
    private List<Game> appendAllGamesFromChildrenCategories(String categoryId, List<Game> games){
        Deque<Category> deque = findAllChildrenCategories(categoryId);

        for (Category category : deque) {
            List<Game> gamesOfChildrenCategory = category.getGames();
            for (Game game : gamesOfChildrenCategory)
                if (!games.contains(game)) games.add(game);
        }
        return games;
    }

}
