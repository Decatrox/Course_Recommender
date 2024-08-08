package com.sumerge.course_recommender.author;

import com.sumerge.course_recommender.mapper.MapStructMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final MapStructMapper mapStructMapper;


    public void addAuthor (AuthorPostDTO author) {
        authorRepository.save(mapStructMapper.authorPostDTOToAuthor(author));
    }

    public AuthorGetDTO getAuthorByEmail (String email) {
        return mapStructMapper.authorToAuthorGetDTO(authorRepository.findByEmail(email));
    }

}
