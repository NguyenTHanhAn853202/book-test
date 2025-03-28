package com.example.entrance_test.controller;

import com.example.entrance_test.model.Author;
import com.example.entrance_test.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/create")
    public String createAuthor(@RequestParam("name") String name,  Model model){
        if(name == null || name.trim().isEmpty()){
            model.addAttribute("errorMessage","*Please enter a name!");
            return "create_author";
        }
        Author author = new Author();
        author.setName(name);
        authorService.create(author);
        return "redirect:/author";
    }

    @PostMapping("/update-name")
    @ResponseBody
    public String updateAuthorName(@RequestParam Long id, @RequestParam String name) {
        if (!authorService.existsById(id)) {
            return "Tác giả không tồn tại";
        }

        Author author = authorService.findById(id);
        author.setName(name);
        authorService.save(author);

        return "Cập nhật thành công";
    }

    @PostMapping("/delete")
    @ResponseBody
    public String deleteAuthor(@RequestParam Long id) {
        if (authorService.existsById(id)) {
            authorService.deleteById(id);
            return "Xóa thành công";
        }
        return "Tác giả không tồn tại";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("currentPage","Author > create");
        return "create_author";
    }

    @GetMapping
    public String listAuthors(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Author> authors = authorService.getAuthorsWithPagination(page, 5);
        model.addAttribute("authors", authors);
        model.addAttribute("currentPage", page);
        model.addAttribute("currentPage","Author > List");

        model.addAttribute("totalPages", authors.getTotalPages());
        return "author";
    }

}
