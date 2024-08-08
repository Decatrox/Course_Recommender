package com.sumerge.course_recommender.author;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/add")
    public void addAuthor(@Valid @RequestBody AuthorPostDTO author) {
        authorService.addAuthor(author);
    }

    @GetMapping("/GetByEmail/{email}")
    public ResponseEntity<AuthorGetDTO> getAuthorByEmail(@Valid @PathVariable String email) {
        return ResponseEntity.ok(authorService.getAuthorByEmail(email));
    }
}
