package com.example.entrance_test.repository;

import com.example.entrance_test.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
}
