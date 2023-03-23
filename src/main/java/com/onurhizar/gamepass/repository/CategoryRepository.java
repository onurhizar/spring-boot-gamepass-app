package com.onurhizar.gamepass.repository;

import com.onurhizar.gamepass.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {

    public Optional<Category> findCategoryByName(String name);
}
