package com.onurhizar.gamepass.containers;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresTestContainer extends PostgreSQLContainer<PostgresTestContainer> {

    public static final String IMAGE_VERSION = "postgres:11.1";
    public static final String DATABASE_NAME = "test_db";
    public static PostgreSQLContainer container;

    public PostgresTestContainer(){
        super(IMAGE_VERSION);
    }

    public static PostgreSQLContainer getInstance(){
        if (container==null) container = new PostgresTestContainer()
                .withUsername("sa")
                .withPassword("sa")
                .withDatabaseName(DATABASE_NAME);
        return container;
    }

    @Override
    public void start(){ // TODO use @DynamicPropertySource
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop(){
    }
}
