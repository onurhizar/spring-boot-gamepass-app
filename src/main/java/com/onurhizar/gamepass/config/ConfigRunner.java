package com.onurhizar.gamepass.config;

import com.onurhizar.gamepass.entity.Game;
import com.onurhizar.gamepass.model.enums.GameCategory;
import com.onurhizar.gamepass.repository.GameRepository;
import com.onurhizar.gamepass.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ConfigRunner implements CommandLineRunner {

    private final GameService gameService;

    @Override
    public void run(String... args) throws Exception {
        gameService.addGame(Game.builder().title("Portal").category(GameCategory.PUZZLE).build());
        gameService.addGame(Game.builder().title("Portal 2").category(GameCategory.PUZZLE).build());
        gameService.addGame(Game.builder().title("Don't Starve Together").category(GameCategory.ACTION).build());
        gameService.addGame(Game.builder().title("Forza Horizon 5").category(GameCategory.RACING).build());
        log.info("games are saved");
    }
}
