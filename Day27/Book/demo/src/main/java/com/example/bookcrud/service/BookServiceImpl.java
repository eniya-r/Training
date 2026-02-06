package com.example.bookcrud.service;

import com.example.bookcrud.dto.BookRequestDTO;
import com.example.bookcrud.dto.BookResponseDTO;
import com.example.bookcrud.exception.BookNotFoundException;
import com.example.bookcrud.mapper.BookMapper;
import com.example.bookcrud.Book;
import com.example.bookcrud.repository.BookRepository;
import com.example.bookcrud.repository.projection.AuthorBookView;
import com.example.bookcrud.repository.projection.TitleBookView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repo;
    private final BookMapper mapper;

    //Pagination + search
    @Override
    public Page<BookResponseDTO> getAll(String author, String keyword, Pageable pageable) {

        Page<Book> page;

        if (author != null && !author.isBlank() && keyword != null && !keyword.isBlank()) {
            page = repo.findByAuthorIgnoreCaseAndTitleContainingIgnoreCase(author, keyword, pageable);
        } else if (author != null && !author.isBlank()) {
            page = repo.findByAuthorIgnoreCase(author, pageable);
        } else if (keyword != null && !keyword.isBlank()) {
            page = repo.findByTitleContainingIgnoreCase(keyword, pageable);
        } else {
            page = repo.findAll(pageable);
        }

        return page.map(mapper::toDto);
    }

    @Override
    public BookResponseDTO getById(int id) {
        Book book = repo.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        return mapper.toDto(book);
    }

    @Override
    public BookResponseDTO create(BookRequestDTO request) {
        Book book = mapper.toEntity(request);
        Book saved = repo.save(book); // createdAt/updatedAt auto-filled
        log.debug("DB CREATE id={} title={} createdAt={}", saved.getId(), saved.getTitle(), saved.getCreatedAt());
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public BookResponseDTO update(int id, BookRequestDTO request) {
        Book existing = repo.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        existing.setTitle(request.getTitle());
        existing.setAuthor(request.getAuthor());
        existing.setPrice(request.getPrice());

        Book saved = repo.save(existing);
        log.debug("DB UPDATE id={} updatedAt={}", saved.getId(), saved.getUpdatedAt());
        return mapper.toDto(saved);
    }

    @Override
    public void delete(int id) {
        if (!repo.existsById(id)) throw new BookNotFoundException(id);
        repo.deleteById(id);
    }

    @Override
    public List<AuthorBookView> getByAuthorLimited(String author) {
        return repo.findByAuthorIgnoreCase(author);
    }

    @Override
    public List<TitleBookView> searchTitleLimited(String keyword) {
        return repo.searchByTitle(keyword);
    }
}