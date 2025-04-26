package com.example.booklibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BooklibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooklibraryApplication.class, args);
    }
}