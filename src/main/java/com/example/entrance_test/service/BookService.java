package com.example.entrance_test.service;

import com.example.entrance_test.model.Book;
import com.example.entrance_test.repository.AuthorRepository;
import com.example.entrance_test.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public Page<Book> getBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.findAll(pageable);
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book saveBook(Book book) {
        authorRepository.findById(book.getAuthor().getId()).ifPresent(author -> {
            author.setBook_quantity(author.getBook_quantity() + 1);
            authorRepository.save(author);
        });
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        Book book = optionalBook.get();

        authorRepository.findById(book.getAuthor().getId()).ifPresent(author -> {
            if (author.getBook_quantity() > 0) { 
                author.setBook_quantity(author.getBook_quantity() - 1);
                authorRepository.save(author);
            }
        });

        bookRepository.deleteById(id);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

}
