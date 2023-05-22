package com.onurhizar.gamepass.repository;

import com.onurhizar.gamepass.model.entity.Category;
import com.onurhizar.gamepass.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {

    Optional<Category> findCategoryByName(String name);

    List<Category> findCategoriesByGames(Game game);

    List<Category> findCategoriesByParentId(String parentId);
}
