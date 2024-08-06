package com.sumerge.course_recommender.author;

import com.sumerge.course_recommender.mapper.MapStructMapper;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final MapStructMapper mapStructMapper;

    public AuthorService(AuthorRepository authorRepository, MapStructMapper mapStructMapper) {
        this.authorRepository = authorRepository;
        this.mapStructMapper = mapStructMapper;
    }

    public void addAuthor (Author author) {
        authorRepository.save(author);
    }

    public AuthorGetDTO getAuthorByEmail (String email) {
        return mapStructMapper.authorToAuthorGetDTO(authorRepository.findByEmail(email));
    }

}
