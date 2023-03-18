package com.onurhizar.gamepass.config;

import com.onurhizar.gamepass.entity.Category;
import com.onurhizar.gamepass.entity.Game;
import com.onurhizar.gamepass.model.enums.GameCategory;
import com.onurhizar.gamepass.repository.GameRepository;
import com.onurhizar.gamepass.service.CategoryService;
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
    private final CategoryService categoryService;

    @Override
    public void run(String... args) throws Exception {
        // addGames();
        // addCategories();
    }


    private void addGames(){
        gameService.addGame(Game.builder().title("Portal").category(GameCategory.PUZZLE).build());
        gameService.addGame(Game.builder().title("Portal 2").category(GameCategory.PUZZLE).build());
        gameService.addGame(Game.builder().title("Don't Starve Together").category(GameCategory.ACTION).build());
        gameService.addGame(Game.builder().title("Forza Horizon 5").category(GameCategory.RACING).build());
        log.info("games are saved");
    }

    private void addCategories(){
        Category baseCategory = categoryService.addCategory(
                Category.builder().name("GAME").build()
        ); // Super Parent Category

        Category actionCategory = categoryService.addCategory(
                Category.builder().parent(baseCategory).name("ACTION").build()
        );

        Category puzzleCategory = categoryService.addCategory(
                Category.builder().parent(baseCategory).name("PUZZLE").build()
        );

        Category fpsCategory = categoryService.addCategory(
                Category.builder().parent(actionCategory).name("FPS").build()
        );
    }
}
