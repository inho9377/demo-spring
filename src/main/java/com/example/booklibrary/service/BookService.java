package com.example.booklibrary.service;

import com.example.booklibrary.dto.BookDTO;

import java.util.List;
import java.util.Optional;

public interface BookService {
    
    // 모든 책 조회
    List<BookDTO> getAllBooks();
    
    // ID로 책 조회
    Optional<BookDTO> getBookById(Long id);
    
    // 제목으로 책 검색
    List<BookDTO> searchBooksByTitle(String title);
    
    // 저자로 책 검색
    List<BookDTO> searchBooksByAuthor(String author);
    
    // 출판년도로 책 검색
    List<BookDTO> searchBooksByPublicationYear(Integer publicationYear);
    
    // ISBN으로 책 검색
    BookDTO getBookByIsbn(String isbn);
    
    // 책 저장 (생성)
    BookDTO createBook(BookDTO bookDTO);
    
    // 책 수정
    BookDTO updateBook(Long id, BookDTO bookDTO);
    
    // ID로 책 삭제
    void deleteBook(Long id);
    
    // 책이 존재하는지 확인
    boolean existsById(Long id);

    // 페이징 처리된 모든 책 조회
    Page<BookDTO> getAllBooksPaged(Pageable pageable);

    // 제목으로 책 검색 (페이징)
    Page<BookDTO> searchBooksByTitlePaged(String title, Pageable pageable);
    
    // 저자로 책 검색 (페이징)
    Page<BookDTO> searchBooksByAuthorPaged(String author, Pageable pageable);
    
    // 출판년도로 책 검색 (페이징)
    Page<BookDTO> searchBooksByPublicationYearPaged(Integer publicationYear, Pageable pageable);
}