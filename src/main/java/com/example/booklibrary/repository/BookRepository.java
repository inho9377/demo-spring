package com.example.booklibrary.repository;

import com.example.booklibrary.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    // 제목으로 책 검색
    List<Book> findByTitleContaining(String title);
    
    // 저자로 책 검색
    List<Book> findByAuthorContaining(String author);
    
    // 출판년도로 책 검색
    List<Book> findByPublicationYear(Integer publicationYear);
    
    // ISBN으로 책 검색
    Book findByIsbn(String isbn);
}