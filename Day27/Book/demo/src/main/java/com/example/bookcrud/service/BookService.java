package com.example.bookcrud.service;

import com.example.bookcrud.dto.BookRequestDTO;
import com.example.bookcrud.dto.BookResponseDTO;
import com.example.bookcrud.repository.projection.AuthorBookView;
import com.example.bookcrud.repository.projection.TitleBookView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    Page<BookResponseDTO> getAll(String author, String keyword, Pageable pageable);
    BookResponseDTO getById(int id);
    BookResponseDTO create(BookRequestDTO request);
    BookResponseDTO update(int id, BookRequestDTO request);
    void delete(int id);

    List<AuthorBookView> getByAuthorLimited(String author);
    List<TitleBookView> searchTitleLimited(String keyword);
}