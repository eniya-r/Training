package com.example.bookcrud;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
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
    public List<Book> getAll() {
        return books;
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable int id) {
        return books.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
    }

    @GetMapping("/search")
    public List<Book> searchByAuthor(@RequestParam String author) {
        return books.stream()
                .filter(b -> b.getAuthor() != null && b.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    @PostMapping
    public Book create(@RequestBody Book book) {
        book.setId(idGen.incrementAndGet());
        books.add(book);
        return book;
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable int id, @RequestBody Book updated) {
        for (Book b : books) {
            if (b.getId() == id) {
                b.setTitle(updated.getTitle());
                b.setAuthor(updated.getAuthor());
                b.setPrice(updated.getPrice());
                return b;
            }
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        boolean removed = books.removeIf(b -> b.getId() == id);
        return removed ? "Book deleted" : "Book not found";
    }
}
