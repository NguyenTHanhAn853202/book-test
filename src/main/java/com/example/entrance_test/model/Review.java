package com.example.entrance_test.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String context;

    @ManyToOne
    @JoinColumn(name = "book_id",nullable = false)
    private Book book;

    public Long getId() {
        return id;
    }

    public Review setId(Long id) {
        this.id = id;
        return this;
    }

    public  String getContext() {
        return context;
    }

    public Review setContext( String context) {
        this.context = context;
        return this;
    }

    public Book getBook() {
        return book;
    }

    public Review setBook(Book book) {
        this.book = book;
        return this;
    }
}
