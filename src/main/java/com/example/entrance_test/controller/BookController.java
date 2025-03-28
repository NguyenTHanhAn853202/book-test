package com.example.entrance_test.controller;

import com.example.entrance_test.model.Book;
import com.example.entrance_test.service.AuthorService;
import com.example.entrance_test.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping
    public String getBooks(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Book> bookPage = bookService.getBooks(page, 5);


        model.addAttribute("books", bookPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookPage.getTotalPages());
        model.addAttribute("breadcrumb","Book > List");

        return "book";
    }

    @PostMapping("/update")
    public String updateBook(@RequestParam Long id, @RequestParam String name) {
        bookService.getBookById(id).ifPresent(book -> {
            book.setName(name);
            bookService.saveBook(book);
        });
        return "redirect:/books";
    }

    @PostMapping("/delete")
    public String deleteBook(@RequestParam Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    @GetMapping("/create")
    public String showCreateBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("breadcrumb","Book > Create");

        return "create_book";
    }

    @PostMapping("/save")
    public String saveBook(@Validated @ModelAttribute("book") Book book, BindingResult result, Model model) {
        if (result.hasErrors() || book.getName().trim().isEmpty() || book.getAuthor() == null) {
            if (book.getName().trim().isEmpty()) {
                model.addAttribute("errorName", "Name is required.");
            }
            if (book.getAuthor() == null) {
                model.addAttribute("errorAuthor", "Author is required.");
            }
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("breadcrumb","Book > Create");
            return "create_book";
        }

        bookService.saveBook(book);
        return "redirect:/books";
    }

}
