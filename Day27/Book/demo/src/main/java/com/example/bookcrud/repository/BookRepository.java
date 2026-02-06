package com.example.bookcrud.repository;

import com.example.bookcrud.Book;
import com.example.bookcrud.repository.projection.AuthorBookView;
import com.example.bookcrud.repository.projection.TitleBookView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    // Pagination + Search (derived queries)
    Page<Book> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

    Page<Book> findByAuthorIgnoreCase(String author, Pageable pageable);

    Page<Book> findByAuthorIgnoreCaseAndTitleContainingIgnoreCase(String author, String keyword, Pageable pageable);

    @Query("SELECT b.id as id, b.title as title, b.price as price " +
            "FROM Book b WHERE LOWER(b.author) = LOWER(:author)")
    List<AuthorBookView> findByAuthorIgnoreCase(@Param("author") String author);

    @Query(value = "SELECT id as id, title as title, author as author " +
            "FROM books WHERE title ILIKE CONCAT('%', :keyword, '%')",
            nativeQuery = true)
    List<TitleBookView> searchByTitle(@Param("keyword") String keyword);
}