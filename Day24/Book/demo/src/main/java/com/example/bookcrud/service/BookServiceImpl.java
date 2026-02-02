package com.example.bookcrud.service;

import com.example.bookcrud.dto.BookRequestDTO;
import com.example.bookcrud.dto.BookResponseDTO;
import com.example.bookcrud.exception.BookNotFoundException;
import com.example.bookcrud.mapper.BookMapper;
import com.example.bookcrud.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookMapper mapper;

    private final List<Book> books = new CopyOnWriteArrayList<>();
    private final AtomicInteger idGen = new AtomicInteger(0);

    @PostConstruct
    public void seedData() {
        books.add(new Book(idGen.incrementAndGet(), "Clean Code", "Robert C. Martin", 799.0));
        books.add(new Book(idGen.incrementAndGet(), "Atomic Habits", "James Clear", 550.0));
        books.add(new Book(idGen.incrementAndGet(), "The Pragmatic Programmer", "Andrew Hunt & David Thomas", 999.0));
        books.add(new Book(idGen.incrementAndGet(), "Head First Java", "Kathy Sierra & Bert Bates", 650.0));
        books.add(new Book(idGen.incrementAndGet(), "Spring in Action", "Craig Walls", 1200.0));
        books.add(new Book(idGen.incrementAndGet(), "Effective Java", "Joshua Bloch", 899.0));

        log.info("Seeded {} books into memory", books.size());
    }

    @Override
    public List<BookResponseDTO> getAll(String author, Double minPrice, Double maxPrice) {
        List<Book> filtered = books.stream()
                .filter(b -> author == null || (b.getAuthor() != null && b.getAuthor().equalsIgnoreCase(author)))
                .filter(b -> minPrice == null || b.getPrice() >= minPrice)
                .filter(b -> maxPrice == null || b.getPrice() <= maxPrice)
                .collect(Collectors.toList());

        log.debug("Fetching books: author={}, minPrice={}, maxPrice={}, count={}",
                author, minPrice, maxPrice, filtered.size());

        return filtered.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public BookResponseDTO getById(int id) {
        Book b = findByIdOrThrow(id);
        return mapper.toDto(b);
    }

    @Override
    public BookResponseDTO create(BookRequestDTO request) {
        Book book = mapper.toEntity(request);
        book.setId(idGen.incrementAndGet());
        books.add(book);

        log.debug("Created book id={} title={}", book.getId(), book.getTitle());
        return mapper.toDto(book);
    }

    @Override
    public BookResponseDTO update(int id, BookRequestDTO request) {
        Book existing = findByIdOrThrow(id);
        existing.setTitle(request.getTitle());
        existing.setAuthor(request.getAuthor());
        existing.setPrice(request.getPrice());

        log.debug("Updated book id={}", id);
        return mapper.toDto(existing);
    }

    @Override
    public void delete(int id) {
        Book existing = findByIdOrThrow(id);
        books.remove(existing);
        log.debug("Deleted book id={}", id);
    }

    private Book findByIdOrThrow(int id) {
        return books.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException(id));
    }
}