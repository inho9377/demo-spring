package com.example.booklibrary.mapper;

import com.example.booklibrary.dto.BookDTO;
import com.example.booklibrary.model.Book;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    // DTO를 엔티티로 변환
    public Book toEntity(BookDTO bookDTO) {
        return Book.builder()
                .id(bookDTO.getId())
                .title(bookDTO.getTitle())
                .author(bookDTO.getAuthor())
                .publisher(bookDTO.getPublisher())
                .publicationYear(bookDTO.getPublicationYear())
                .isbn(bookDTO.getIsbn())
                .description(bookDTO.getDescription())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    // 엔티티를 DTO로 변환
    public BookDTO toDTO(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .publicationYear(book.getPublicationYear())
                .isbn(book.getIsbn())
                .description(book.getDescription())
                .build();
    }

    // 엔티티 리스트를 DTO 리스트로 변환
    public List<BookDTO> toDTOList(List<Book> books) {
        return books.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}