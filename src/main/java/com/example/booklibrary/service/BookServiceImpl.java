package com.example.booklibrary.service;

import com.example.booklibrary.dto.BookDTO;
import com.example.booklibrary.exception.ResourceNotFoundException;
import com.example.booklibrary.mapper.BookMapper;
import com.example.booklibrary.model.Book;
import com.example.booklibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "bookCache", key = "'allBooks'")
    @Loggable("모든 책 조회")
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return bookMapper.toDTOList(books);
    }
    
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "bookCache", key = "'book_' + #id")
    public Optional<BookDTO> getBookById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> searchBooksByTitle(String title) {
        List<Book> books = bookRepository.findByTitleContaining(title);
        return bookMapper.toDTOList(books);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> searchBooksByAuthor(String author) {
        List<Book> books = bookRepository.findByAuthorContaining(author);
        return bookMapper.toDTOList(books);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> searchBooksByPublicationYear(Integer publicationYear) {
        List<Book> books = bookRepository.findByPublicationYear(publicationYear);
        return bookMapper.toDTOList(books);
    }

    @Override
    @Transactional(readOnly = true)
    public BookDTO getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn);
        return book != null ? bookMapper.toDTO(book) : null;
    }

    @Override
    @CachePut(value = "bookCache", key = "'book_' + #result.id")
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = bookMapper.toEntity(bookDTO);
        book.setCreatedAt(LocalDateTime.now());
        book.setUpdatedAt(LocalDateTime.now());
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDTO(savedBook);
    }
    
    @Override
    @CachePut(value = "bookCache", key = "'book_' + #id")
    @CacheEvict(value = "bookCache", key = "'allBooks'")
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        
        Book book = bookMapper.toEntity(bookDTO);
        book.setId(id);
        book.setCreatedAt(existingBook.getCreatedAt());
        book.setUpdatedAt(LocalDateTime.now());
        
        Book updatedBook = bookRepository.save(book);
        return bookMapper.toDTO(updatedBook);
    }
    
    @Override
    @Caching(evict = {
        @CacheEvict(value = "bookCache", key = "'book_' + #id"),
        @CacheEvict(value = "bookCache", key = "'allBooks'")
    })
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return bookRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookDTO> getAllBooksPaged(Pageable pageable) {
        Page<Book> booksPage = bookRepository.findAll(pageable);
        return booksPage.map(bookMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookDTO> searchBooksByTitlePaged(String title, Pageable pageable) {
        Page<Book> booksPage = bookRepository.findByTitleContaining(title, pageable);
        return booksPage.map(bookMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookDTO> searchBooksByAuthorPaged(String author, Pageable pageable) {
        Page<Book> booksPage = bookRepository.findByAuthorContaining(author, pageable);
        return booksPage.map(bookMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookDTO> searchBooksByPublicationYearPaged(Integer publicationYear, Pageable pageable) {
        Page<Book> booksPage = bookRepository.findByPublicationYear(publicationYear, pageable);
        return booksPage.map(bookMapper::toDTO);
    }
}