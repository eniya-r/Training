package com.example.bookcrud.service;

import com.example.bookcrud.dto.BookRequestDTO;
import com.example.bookcrud.dto.BookResponseDTO;
import com.example.bookcrud.exception.BookNotFoundException;
import com.example.bookcrud.mapper.BookMapper;
import com.example.bookcrud.Book;
import com.example.bookcrud.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repo;
    private final BookMapper mapper;

    @Override
    public List<BookResponseDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponseDTO getById(int id) {
        Book book = repo.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return mapper.toDto(book);
    }

    @Override
    public BookResponseDTO create(BookRequestDTO request) {
        Book book = mapper.toEntity(request);
        Book saved = repo.save(book);
        log.debug("Saved book into DB id={} title={}", saved.getId(), saved.getTitle());
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public BookResponseDTO update(int id, BookRequestDTO request) {
        Book existing = repo.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        existing.setTitle(request.getTitle());
        existing.setAuthor(request.getAuthor());
        existing.setPrice(request.getPrice());

        return mapper.toDto(existing);
    }

    @Override
    public void delete(int id) {
        if (!repo.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        repo.deleteById(id);
    }

    //Custom Query 1 usage
    @Override
    public List<BookResponseDTO> getByAuthor(String author) {
        return repo.findByAuthorIgnoreCase(author)
                .stream().map(mapper::toDto).collect(Collectors.toList());
    }

    //Custom Query 2 usage
    @Override
    public List<BookResponseDTO> searchByTitle(String keyword) {
        return repo.searchByTitle(keyword)
                .stream().map(mapper::toDto).collect(Collectors.toList());
    }
}