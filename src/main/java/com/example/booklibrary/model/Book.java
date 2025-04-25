package com.example.booklibrary.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "제목은 필수입니다")
    @Size(min = 1, max = 100, message = "제목은 1-100자 사이여야 합니다")
    private String title;

    @NotBlank(message = "저자는 필수입니다")
    @Size(min = 1, max = 50, message = "저자는 1-50자 사이여야 합니다")
    private String author;

    @NotBlank(message = "출판사는 필수입니다")
    private String publisher;

    @NotNull(message = "출판년도는 필수입니다")
    @Positive(message = "출판년도는 양수여야 합니다")
    private Integer publicationYear;

    @NotBlank(message = "ISBN은 필수입니다")
    private String isbn;

    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}