package com.example.booklibrary.controller;

import com.example.booklibrary.dto.BookDTO;
import com.example.booklibrary.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // 모든 책 조회
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    // ID로 책 조회
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 책 생성
    @PostMapping
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) {
        BookDTO createdBook = bookService.createBook(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    // 책 수정
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO) {
        BookDTO updatedBook = bookService.updateBook(id, bookDTO);
        return ResponseEntity.ok(updatedBook);
    }

    // 책 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    // 제목으로 책 검색
    @GetMapping("/search/title")
    public ResponseEntity<List<BookDTO>> searchBooksByTitle(@RequestParam String title) {
        List<BookDTO> books = bookService.searchBooksByTitle(title);
        return ResponseEntity.ok(books);
    }

    // 저자로 책 검색
    @GetMapping("/search/author")
    public ResponseEntity<List<BookDTO>> searchBooksByAuthor(@RequestParam String author) {
        List<BookDTO> books = bookService.searchBooksByAuthor(author);
        return ResponseEntity.ok(books);
    }

    // 출판년도로 책 검색
    @GetMapping("/search/year")
    public ResponseEntity<List<BookDTO>> searchBooksByYear(@RequestParam Integer year) {
        List<BookDTO> books = bookService.searchBooksByPublicationYear(year);
        return ResponseEntity.ok(books);
    }

    // ISBN으로 책 검색
    @GetMapping("/search/isbn")
    public ResponseEntity<BookDTO> getBookByIsbn(@RequestParam String isbn) {
        BookDTO book = bookService.getBookByIsbn(isbn);
        return book != null ? ResponseEntity.ok(book) : ResponseEntity.notFound().build();
    }

    // 페이징된 책 조회
    @GetMapping("/paged")
    public ResponseEntity<Page<BookDTO>> getAllBooksPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) 
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        
        Pageable pageable = PageRequest.of(page, size, sortDirection, sort);
        Page<BookDTO> books = bookService.getAllBooksPaged(pageable);
        
        return ResponseEntity.ok(books);
    }

    // 제목으로 책 검색 (페이징)
    @GetMapping("/search/title/paged")
    public ResponseEntity<Page<BookDTO>> searchBooksByTitlePaged(
            @RequestParam String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<BookDTO> books = bookService.searchBooksByTitlePaged(title, pageable);
        
        return ResponseEntity.ok(books);
    }

    // 저자로 책 검색 (페이징)
    @GetMapping("/search/author/paged")
    public ResponseEntity<Page<BookDTO>> searchBooksByAuthorPaged(
            @RequestParam String author,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<BookDTO> books = bookService.searchBooksByAuthorPaged(author, pageable);
        
        return ResponseEntity.ok(books);
    }

    // 출판년도로 책 검색 (페이징)
    @GetMapping("/search/year/paged")
    public ResponseEntity<Page<BookDTO>> searchBooksByPublicationYearPaged(
            @RequestParam Integer year,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<BookDTO> books = bookService.searchBooksByPublicationYearPaged(year, pageable);
        
        return ResponseEntity.ok(books);
    }

    // 모든 책 조회 (HATEOAS 적용)
    @GetMapping("/hateoas")
    public ResponseEntity<CollectionModel<BookModel>> getAllBooksWithHateoas() {
        List<BookDTO> books = bookService.getAllBooks();
        
        List<BookModel> bookModels = books.stream()
                .map(BookModel::of)
                .collect(Collectors.toList());
        
        CollectionModel<BookModel> collectionModel = CollectionModel.of(
                bookModels,
                linkTo(methodOn(BookController.class).getAllBooksWithHateoas()).withSelfRel(),
                linkTo(methodOn(BookController.class).getAllBooks()).withRel("books-without-hateoas")
        );
        
        return ResponseEntity.ok(collectionModel);
    }

    // ID로 책 조회 (HATEOAS 적용)
    @GetMapping("/{id}/hateoas")
    public ResponseEntity<BookModel> getBookByIdWithHateoas(@PathVariable Long id) {
        return bookService.getBookById(id)
                .map(BookModel::of)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}