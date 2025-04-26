package com.example.booklibrary.config;

import com.example.booklibrary.model.Book;
import com.example.booklibrary.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
public class DatabaseInitializer {

    // 개발 환경에서만 샘플 데이터 초기화
    @Bean
    @Profile("dev")
    public CommandLineRunner initDatabase(BookRepository bookRepository) {
        return args -> {
            // 개발 환경에서만 샘플 데이터 생성
            LocalDateTime now = LocalDateTime.now();
            
            Book book1 = Book.builder()
                    .title("스프링 부트 인 액션")
                    .author("크레이그 월즈")
                    .publisher("한빛미디어")
                    .publicationYear(2022)
                    .isbn("9788980782970")
                    .description("스프링 부트를 이용한 애플리케이션 개발 입문서")
                    .createdAt(now)
                    .updatedAt(now)
                    .build();
            
            Book book2 = Book.builder()
                    .title("토비의 스프링")
                    .author("이일민")
                    .publisher("에이콘")
                    .publicationYear(2021)
                    .isbn("9791161755748")
                    .description("스프링 프레임워크의 원리와 이해")
                    .createdAt(now)
                    .updatedAt(now)
                    .build();
            
            Book book3 = Book.builder()
                    .title("자바 ORM 표준 JPA 프로그래밍")
                    .author("김영한")
                    .publisher("에이콘")
                    .publicationYear(2020)
                    .isbn("9791162342404")
                    .description("JPA와 하이버네이트를 사용한 자바 ORM 프로그래밍")
                    .createdAt(now)
                    .updatedAt(now)
                    .build();
            
            bookRepository.saveAll(Arrays.asList(book1, book2, book3));
        };
    }
}