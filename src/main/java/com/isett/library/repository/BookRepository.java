package com.isett.library.repository;

import com.isett.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    Book findByIsbn(String isbn);
    void deleteBookByIsbn(String isbn);
}