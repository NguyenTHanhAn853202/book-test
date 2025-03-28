package com.example.entrance_test.repository;

import com.example.entrance_test.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long> {
}
