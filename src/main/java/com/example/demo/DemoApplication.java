package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sun.tools.jar.CommandLine;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Slf4j
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(UserService userService) {
        return (String... strings) -> {

            CompletableFuture.runAsync(() -> {
                log.info("creating a new user");
                userService.createNew();
                log.info("new user created");
            }).whenComplete((o, ex) -> {
                log.error(ex.getMessage(), ex);
            });

            CompletableFuture.supplyAsync(() -> {
                log.info("getting user by id 1");
                return userService.countUsers();
            }).whenComplete((count, ex) -> {
                log.info("user count got from db: {}", count);
            });

        };
    }
}
