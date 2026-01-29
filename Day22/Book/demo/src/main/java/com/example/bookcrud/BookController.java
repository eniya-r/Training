package com.example.bookcrud.controller;

import com.example.bookcrud.dto.BookRequestDTO;
import com.example.bookcrud.dto.BookResponseDTO;
import com.example.bookcrud.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/books")
@Validated
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    // GET /books?author=...&minPrice=...&maxPrice=...
    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAll(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) @PositiveOrZero(message = "minPrice must be >= 0") Double minPrice,
            @RequestParam(required = false) @Positive(message = "maxPrice must be > 0") Double maxPrice
    ) {
        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.getAll(author, minPrice, maxPrice));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> create(@Valid @RequestBody BookRequestDTO request) {
        BookResponseDTO created = service.create(request);
        log.info("POST /books created id={}", created.getId());
        return ResponseEntity.created(URI.create("/books/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> update(@PathVariable int id,
                                                  @Valid @RequestBody BookRequestDTO request) {
        BookResponseDTO updated = service.update(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}