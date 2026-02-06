package com.example.bookcrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookCrudApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookCrudApplication.class, args);
    }
}