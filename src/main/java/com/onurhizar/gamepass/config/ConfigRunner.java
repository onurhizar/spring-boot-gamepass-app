package com.onurhizar.gamepass.config;

import com.onurhizar.gamepass.entity.Category;
import com.onurhizar.gamepass.entity.Game;
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
    }

}
