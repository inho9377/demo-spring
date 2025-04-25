package com.example.booklibrary.service;

import com.example.booklibrary.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    
    // 모든 책 조회
    List<Book> getAllBooks();
    
    // ID로 책 조회
    Optional<Book> getBookById(Long id);
    
    // 제목으로 책 검색
    List<Book> searchBooksByTitle(String title);
    
    // 저자로 책 검색
    List<Book> searchBooksByAuthor(String author);
    
    // 출판년도로 책 검색
    List<Book> searchBooksByPublicationYear(Integer publicationYear);
    
    // ISBN으로 책 검색
    Book getBookByIsbn(String isbn);
    
    // 책 저장 (생성 또는 수정)
    Book saveBook(Book book);
    
    // ID로 책 삭제
    void deleteBook(Long id);
    
    // 책이 존재하는지 확인
    boolean existsById(Long id);
}