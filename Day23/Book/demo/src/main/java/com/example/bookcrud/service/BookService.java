package com.example.bookcrud.service;

import com.example.bookcrud.dto.BookRequestDTO;
import com.example.bookcrud.dto.BookResponseDTO;

import java.util.List;

public interface BookService {
    List<BookResponseDTO> getAll(String author, Double minPrice, Double maxPrice);
    BookResponseDTO getById(int id);
    BookResponseDTO create(BookRequestDTO request);
    BookResponseDTO update(int id, BookRequestDTO request);
    void delete(int id);
}