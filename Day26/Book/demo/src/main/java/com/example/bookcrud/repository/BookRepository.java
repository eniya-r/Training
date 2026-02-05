package com.example.bookcrud.repository;

import com.example.bookcrud.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    // ✅ Custom Query #1 (JPQL)
    @Query("SELECT b FROM Book b WHERE LOWER(b.author) = LOWER(:author)")
    List<Book> findByAuthorIgnoreCase(@Param("author") String author);

    // ✅ Custom Query #2 (Native SQL using ILIKE for PostgreSQL)
    @Query(value = "SELECT * FROM books WHERE title ILIKE CONCAT('%', :keyword, '%')", nativeQuery = true)
    List<Book> searchByTitle(@Param("keyword") String keyword);
}