package com.sumerge.course_recommender.author;

import com.sumerge.course_recommender.mapper.MapStructMapper;
import jakarta.persistence.EntityExistsException;
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
        if (authorRepository.existsByEmail(author.getEmail())){
            throw new EntityExistsException("Author with this Email ");
        }
        authorRepository.save(mapStructMapper.authorPostDTOToAuthor(author));
    }

    public AuthorGetDTO getAuthorByEmail (String email) {
        Author author = authorRepository.findByEmail(email);
        if (author == null) {
            throw new EntityNotFoundException();
        }
        return mapStructMapper.authorToAuthorGetDTO(author);
    }

}
