package com.example.entrance_test.controller;

import com.example.entrance_test.model.Book;
import com.example.entrance_test.model.Review;
import com.example.entrance_test.service.BookService;
import com.example.entrance_test.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private BookService bookService;

    @GetMapping("/create")
    public String showCreateReviewForm(Model model) {
        model.addAttribute("review", new Review());
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("currentPage","Review > Create");
        return "create_review";
    }

    @PostMapping("/create")
    public String saveReview(@Validated @ModelAttribute("review") Review review, BindingResult result, Model model) {
        boolean hasErrors = false;

        if (result.hasErrors()) {
            hasErrors = true;
        }

        if (review.getBook() == null || review.getBook().getId() == null) {
            model.addAttribute("errorBook", "Book cannot be empty");
            hasErrors = true;
        }

        if (review.getContext() == null || review.getContext().trim().isEmpty()) {
            model.addAttribute("errorContext", "Context cannot be empty");
            hasErrors = true;
        }

        if (hasErrors) {
            model.addAttribute("books", bookService.getAllBooks());
            model.addAttribute("currentPage","Review > Create");

            return "create_review";
        }

        reviewService.saveReview(review);
        return "redirect:/reviews";
    }

    @GetMapping
    public String listReviews(Model model, @RequestParam(defaultValue = "1") int page) {
        int pageSize = 5;
        Page<Review> reviewPage = reviewService.getReviewsPaginated(page, pageSize);
        model.addAttribute("reviews", reviewPage.getContent());
        model.addAttribute("totalPages", reviewPage.getTotalPages());
        model.addAttribute("currentPage","Review > List");

        return "review";
    }

    @PostMapping("/update")
    public String updateReview(@RequestParam Long id, @RequestParam String context) {
        Review review = reviewService.getReviewById(id);
        if (review != null) {
            review.setContext(context);
            reviewService.saveReview(review);
        }
        return "redirect:/reviews";
    }

    @PostMapping("/delete")
    public String deleteReview(@RequestParam Long id) {
        reviewService.deleteReview(id);
        return "redirect:/reviews";
    }
}
