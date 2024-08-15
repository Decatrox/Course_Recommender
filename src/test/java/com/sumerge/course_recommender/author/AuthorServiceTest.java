package com.sumerge.course_recommender.author;

import com.sumerge.course_recommender.exception_handling.custom_exceptions.AuthorAlreadyExistsException;
import com.sumerge.course_recommender.exception_handling.custom_exceptions.AuthorNotFoundException;
import com.sumerge.course_recommender.mapper.MapStructMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;


import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @InjectMocks
    private AuthorService authorService;

    @Mock
    private MapStructMapper mapStructMapper;
    @Mock
    private AuthorRepository authorRepository;

    @Test
    void whenAddAuthor_callRepositoryAddAuthor_ifValidAuthor() {
        String name = "Author Name"; String email = "author@gmail.com"; Date birthdate = new Date();
        AuthorPostDTO authorPostDTO = new AuthorPostDTO();
        Author author = new Author();
        authorPostDTO.setName(name); authorPostDTO.setEmail(email); authorPostDTO.setBirthdate(birthdate);
        author.setName(name); author.setEmail(email); author.setBirthdate(birthdate);

        org.mockito.Mockito.when(mapStructMapper.authorPostDTOToAuthor(authorPostDTO)).thenReturn(author);

        authorService.addAuthor(authorPostDTO);

        ArgumentCaptor<Author> authorCaptor = ArgumentCaptor.forClass(Author.class);
        org.mockito.Mockito.verify(authorRepository).save(authorCaptor.capture());
        Author authorSaved = authorCaptor.getValue();

        assertThat(authorSaved).isEqualTo(author);
    }

    @Test
    void whenAddAuthor_throwAlreadyExistsException_ifAuthorAlreadyExists() {
        AuthorPostDTO authorPostDTO = new AuthorPostDTO();
        String email = "author@gmail.com";
        authorPostDTO.setEmail(email);
        org.mockito.Mockito.when(authorRepository.existsByEmail(email)).thenReturn(true);
        assertThatThrownBy(() -> authorService.addAuthor(authorPostDTO))
                .isInstanceOf(AuthorAlreadyExistsException.class);

    }

    @Test
    void whenGetAuthorByEmail_callRepositoryGetAuthor_ifValidAuthor() {
        String name = "Author Name"; String email = "Author Email"; Date birthdate = new Date();
        Author author = new Author();
        AuthorGetDTO authorGetDTO = new AuthorGetDTO();
        authorGetDTO.setName(name); authorGetDTO.setEmail(email); authorGetDTO.setBirthdate(birthdate);
        author.setName(name); author.setEmail(email); author.setBirthdate(birthdate);

        org.mockito.Mockito.when(authorRepository.findByEmail(email)).thenReturn(author);
        org.mockito.Mockito.when(mapStructMapper.authorToAuthorGetDTO(author)).thenReturn(authorGetDTO);

        AuthorGetDTO returnedAuthor = authorService.getAuthorByEmail(email);

        assertThat(returnedAuthor).usingRecursiveComparison().ignoringFields("id").isEqualTo(author);

        //Test if Author is null
        String invalidEmail = "a";
        org.mockito.Mockito.when(authorRepository.findByEmail(invalidEmail)).thenReturn(null);
        assertThatThrownBy(() -> authorService.getAuthorByEmail(invalidEmail))
                .isInstanceOf(AuthorNotFoundException.class);

    }
}