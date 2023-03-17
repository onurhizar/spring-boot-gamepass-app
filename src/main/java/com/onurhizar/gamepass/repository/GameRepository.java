package com.onurhizar.gamepass.repository;

import com.onurhizar.gamepass.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, String> {
}
