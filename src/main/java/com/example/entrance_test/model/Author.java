package com.example.entrance_test.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "book_quantity")
    private int book_quantity = 0;

    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> book = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBook_quantity() {
        return book_quantity;
    }

    public List<Book> getBook() {
        return book;
    }

    public Author setId(Long id) {
        this.id = id;
        return this;
    }

    public Author setName(String name) {
        this.name = name;
        return this;
    }

    public Author setBook_quantity(int book_quantity) {
        this.book_quantity = book_quantity;
        return this;
    }

    public Author setBook(List<Book> book) {
        this.book = book;
        return this;
    }
}
