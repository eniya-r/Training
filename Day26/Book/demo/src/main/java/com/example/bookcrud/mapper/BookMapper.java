package com.example.bookcrud.mapper;

import com.example.bookcrud.dto.BookRequestDTO;
import com.example.bookcrud.dto.BookResponseDTO;
import com.example.bookcrud.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public Book toEntity(BookRequestDTO dto) {
        return new Book(null, dto.getTitle(), dto.getAuthor(), dto.getPrice());
    }

    public BookResponseDTO toDto(Book book) {
        return new BookResponseDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getPrice());
    }
}