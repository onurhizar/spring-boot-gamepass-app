package com.onurhizar.gamepass.config;

import com.onurhizar.gamepass.entity.Game;
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

        gameService.addGame(Game.builder().title("Portal").build());
        gameService.addGame(Game.builder().title("Portal 2").build());
        gameService.addGame(Game.builder().title("Don't Starve Together").build());
        gameService.addGame(Game.builder().title("Antichamber").build());
        log.info("games are saved");
    }
}
