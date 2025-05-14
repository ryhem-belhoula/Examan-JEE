package com.isett.library.service;

import com.isett.library.model.Book;
import com.isett.library.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Cacheable(value = "books")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Cacheable(value = "books", key = "#isbn")
    public Book getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @Override
    @CachePut(value = "books", key = "#result.isbn")
    @CacheEvict(value = "books", allEntries = true)
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @CachePut(value = "books", key = "#isbn")
    @CacheEvict(value = "books", allEntries = true)
    public Book updateBook(String isbn, Book updatedBook) {
        Book bookA = bookRepository.findByIsbn(isbn);
        bookA.setTitle(updatedBook.getTitle());
        bookA.setAuthor(updatedBook.getAuthor());
        return bookRepository.save(bookA);
    }

    @Override
    @CacheEvict(value = "books", allEntries = true)
    @Transactional
    public void deleteBook(String isbn) {
        bookRepository.deleteBookByIsbn(isbn);
    }
}
