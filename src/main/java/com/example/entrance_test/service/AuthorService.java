package com.example.entrance_test.service;

import com.example.entrance_test.model.Author;
import com.example.entrance_test.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    public boolean create(Author author){
        Author newAuthor = authorRepository.save(author);
        return newAuthor!=null;
    }

    public Author findById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    public void save(Author author) {
        authorRepository.save(author);
    }

    public Page<Author> getAuthorsWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return authorRepository.findAll(pageable);
    }

    public boolean existsById(Long id) {
        return authorRepository.existsById(id);
    }

    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

}
