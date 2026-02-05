package com.example.bookcrud.service;

import com.example.bookcrud.dto.BookRequestDTO;
import com.example.bookcrud.dto.BookResponseDTO;

import java.util.List;

public interface BookService {
    List<BookResponseDTO> getAll();
    BookResponseDTO getById(int id);
    BookResponseDTO create(BookRequestDTO request);
    BookResponseDTO update(int id, BookRequestDTO request);
    void delete(int id);

    // custom query endpoints
    List<BookResponseDTO> getByAuthor(String author);
    List<BookResponseDTO> searchByTitle(String keyword);
}