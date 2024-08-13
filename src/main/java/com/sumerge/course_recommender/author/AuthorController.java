package com.sumerge.course_recommender.author;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @Operation(summary = "Add a new author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Author added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
    })
    @PostMapping
    public void addAuthor(@Valid @RequestBody AuthorPostDTO author) {
        authorService.addAuthor(author);
    }


    @Operation(summary = "Get an author by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author found"),
            @ApiResponse(responseCode = "404", description = "Author already exists"),
    })
    @GetMapping("/{email}")
    public ResponseEntity<AuthorGetDTO> getAuthorByEmail(@PathVariable String email) {
        return ResponseEntity.ok(authorService.getAuthorByEmail(email));
    }
}
