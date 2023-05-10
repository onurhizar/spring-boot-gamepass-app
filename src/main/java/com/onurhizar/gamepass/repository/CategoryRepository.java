package com.onurhizar.gamepass.repository;

import com.onurhizar.gamepass.model.entity.Category;
import com.onurhizar.gamepass.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {

    public Optional<Category> findCategoryByName(String name);

    public List<Category> findCategoriesByGames(Game game);
}
