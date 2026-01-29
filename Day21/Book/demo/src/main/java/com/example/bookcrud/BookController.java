package com.example.bookcrud;

import com.example.bookcrud.dto.BookRequestDTO;
import com.example.bookcrud.dto.BookResponseDTO;
import com.example.bookcrud.Book;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.net.URI;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
@Validated
public class BookController {

    private final List<Book> books = new CopyOnWriteArrayList<>();
    private final AtomicInteger idGen = new AtomicInteger(0);

    public BookController() {
        books.add(new Book(idGen.incrementAndGet(), "Clean Code", "Robert C. Martin", 799.0));
        books.add(new Book(idGen.incrementAndGet(), "Atomic Habits", "James Clear", 550.0));
        books.add(new Book(idGen.incrementAndGet(), "The Pragmatic Programmer", "Andrew Hunt & David Thomas", 999.0));
        books.add(new Book(idGen.incrementAndGet(), "Head First Java", "Kathy Sierra & Bert Bates", 650.0));
        books.add(new Book(idGen.incrementAndGet(), "Spring in Action", "Craig Walls", 1200.0));
        books.add(new Book(idGen.incrementAndGet(), "Effective Java", "Joshua Bloch", 899.0));
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAll(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) @PositiveOrZero(message = "minPrice must be >= 0") Double minPrice,
            @RequestParam(required = false) @Positive(message = "maxPrice must be > 0") Double maxPrice
    ) {
        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            return ResponseEntity.badRequest().build();
        }

        List<Book> filtered = books.stream()
                .filter(b -> author == null || (b.getAuthor() != null && b.getAuthor().equalsIgnoreCase(author)))
                .filter(b -> minPrice == null || b.getPrice() >= minPrice)
                .filter(b -> maxPrice == null || b.getPrice() <= maxPrice)
                .collect(Collectors.toList());

        return ResponseEntity.ok(toDtoList(filtered));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getById(@PathVariable int id) {
        Book b = findById(id);
        if (b == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(toDto(b));
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> create(@Valid @RequestBody BookRequestDTO request) {
        Book newBook = fromDto(request);
        newBook.setId(idGen.incrementAndGet());
        books.add(newBook);

        BookResponseDTO body = toDto(newBook);
        URI location = URI.create("/books/" + newBook.getId());
        return ResponseEntity.created(location).body(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> update(@PathVariable int id,
                                                  @Valid @RequestBody BookRequestDTO request) {
        Book existing = findById(id);
        if (existing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        existing.setTitle(request.getTitle());
        existing.setAuthor(request.getAuthor());
        existing.setPrice(request.getPrice());

        return ResponseEntity.ok(toDto(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        boolean removed = books.removeIf(b -> b.getId() == id);
        return removed ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    private Book findById(int id) {
        return books.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
    }

    private Book fromDto(BookRequestDTO dto) {
        return new Book(0, dto.getTitle(), dto.getAuthor(), dto.getPrice());
    }

    private BookResponseDTO toDto(Book b) {
        return new BookResponseDTO(b.getId(), b.getTitle(), b.getAuthor(), b.getPrice());
    }

    private List<BookResponseDTO> toDtoList(List<Book> list) {
        return list.stream().map(this::toDto).collect(Collectors.toList());
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleBodyValidation(
            org.springframework.web.bind.MethodArgumentNotValidException ex) {

        Map<String, Object> errors = new LinkedHashMap<>();
        errors.put("status", 400);
        errors.put("error", "Bad Request");

        Map<String, String> fieldErrors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(fe -> fieldErrors.put(fe.getField(), fe.getDefaultMessage()));
        errors.put("fieldErrors", fieldErrors);

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", 400);
        body.put("error", "Bad Request");
        body.put("violations", ex.getConstraintViolations()
                .stream()
                .map(v -> Map.of("param", v.getPropertyPath().toString(), "message", v.getMessage()))
                .collect(Collectors.toList()));
        return ResponseEntity.badRequest().body(body);
    }
}
