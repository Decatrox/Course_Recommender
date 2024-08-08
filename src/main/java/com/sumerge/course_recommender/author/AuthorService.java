package com.sumerge.course_recommender.author;

import com.sumerge.course_recommender.mapper.MapStructMapper;
import jakarta.persistence.EntityNotFoundException;
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
//        return mapStructMapper.authorToAuthorGetDTO(authorRepository.findByEmail(email));
        Author author = authorRepository.findByEmail(email);
        if (author == null) {
            throw new EntityNotFoundException();
        }
        return mapStructMapper.authorToAuthorGetDTO(author);
    }

}
