package com.onurhizar.gamepass.repository;

import com.onurhizar.gamepass.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
