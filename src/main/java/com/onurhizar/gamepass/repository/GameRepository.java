package com.onurhizar.gamepass.repository;

import com.onurhizar.gamepass.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, String> {
}
