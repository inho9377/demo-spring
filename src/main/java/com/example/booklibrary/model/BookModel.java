package com.example.booklibrary.model;

import com.example.booklibrary.controller.BookController;
import com.example.booklibrary.dto.BookDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
@AllArgsConstructor
@Relation(collectionRelation = "books", itemRelation = "book")
public class BookModel extends RepresentationModel<BookModel> {
    
    private final Long id;
    private final String title;
    private final String author;
    private final String publisher;
    private final Integer publicationYear;
    private final String isbn;
    private final String description;
    
    public static BookModel of(BookDTO bookDTO) {
        BookModel bookModel = new BookModel(
                bookDTO.getId(),
                bookDTO.getTitle(),
                bookDTO.getAuthor(),
                bookDTO.getPublisher(),
                bookDTO.getPublicationYear(),
                bookDTO.getIsbn(),
                bookDTO.getDescription()
        );
        
        // 자기 자신에 대한 링크 추가
        bookModel.add(linkTo(methodOn(BookController.class)
                .getBookById(bookDTO.getId())).withSelfRel());
        
        // 모든 책 목록에 대한 링크 추가
        bookModel.add(linkTo(methodOn(BookController.class)
                .getAllBooks()).withRel("all-books"));
        
        // 같은 저자의 책들에 대한 링크 추가
        bookModel.add(linkTo(methodOn(BookController.class)
                .searchBooksByAuthor(bookDTO.getAuthor())).withRel("books-by-author"));
        
        return bookModel;
    }
}