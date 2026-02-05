package com.example.bookcrud;

import com.example.bookcrud.dto.BookRequestDTO;
import com.example.bookcrud.dto.BookResponseDTO;
import com.example.bookcrud.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/books")
@Validated
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    // Built-in CRUD operations

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> create(@Valid @RequestBody BookRequestDTO request) {
        BookResponseDTO created = service.create(request);
        return ResponseEntity.created(URI.create("/books/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> update(@PathVariable int id,
                                                  @Valid @RequestBody BookRequestDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    // Example: GET /books/by-author?author=Robert%20C.%20Martin
    @GetMapping("/by-author")
    public ResponseEntity<List<BookResponseDTO>> getByAuthor(@RequestParam String author) {
        return ResponseEntity.ok(service.getByAuthor(author));
    }

    // Example: GET /books/search-title?keyword=clean
    @GetMapping("/search-title")
    public ResponseEntity<List<BookResponseDTO>> searchByTitle(@RequestParam String keyword) {
        return ResponseEntity.ok(service.searchByTitle(keyword));
    }
}