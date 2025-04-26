package com.example.booklibrary.repository;

import com.example.booklibrary.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    // 제목으로 책 검색 (페이징)
    Page<Book> findByTitleContaining(String title, Pageable pageable);
    
    // 저자로 책 검색 (페이징)
    Page<Book> findByAuthorContaining(String author, Pageable pageable);
    
    // 출판년도로 책 검색 (페이징)
    Page<Book> findByPublicationYear(Integer publicationYear, Pageable pageable);
    
    // 기존 메서드 유지
    List<Book> findByTitleContaining(String title);
    List<Book> findByAuthorContaining(String author);
    List<Book> findByPublicationYear(Integer publicationYear);
    Book findByIsbn(String isbn);
}