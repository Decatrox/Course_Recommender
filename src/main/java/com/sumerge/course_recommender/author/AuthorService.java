package com.sumerge.course_recommender.author;

import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void addAuthor (Author author) {
        authorRepository.save(author);
    }

    public Author getAuthorByEmail (String email) {
        return authorRepository.findByEmail(email);
    }

}
