package com.sumerge.course_recommender.author;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/add")
    public void AddAuthor(@RequestBody Author author) {
        authorService.addAuthor(author);
    }

    @GetMapping("/GetByEmail")
    public Author getAuthorByEmail(@RequestParam String email) {
        return authorService.getAuthorByEmail(email);
    }
}
