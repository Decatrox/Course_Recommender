package com.sumerge.course_recommender.author;

import jakarta.validation.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.Date;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

// No need to test this since the methods here are from JPARepository but I wrote the tests for learning purposes
@DataJpaTest
@ActiveProfiles("test")
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldFindAuthorByEmail() {
        //Given
        String email = "test@test.com";
        Author testAuthor = new Author();
        testAuthor.setName("Bob");
        testAuthor.setEmail(email);
        testAuthor.setBirthdate(new Date());
        underTest.save(testAuthor);

        //When
        Author resultAuthor = underTest.findByEmail(email);

        //Then
        assertThat(resultAuthor).isEqualTo(testAuthor);
    }

    @Test
    void itShouldNotFindAuthorByWrongEmail() {
        //Given
        String email = "test@test.com";
        Author testAuthor = new Author();
        testAuthor.setName("Bob");
        testAuthor.setEmail(email);
        testAuthor.setBirthdate(new Date());
        underTest.save(testAuthor);

        //When
        Author resultAuthor = underTest.findByEmail("a" + email);

        //Then
        assertThat(resultAuthor).isNotEqualTo(testAuthor);
    }

//    @Test
//    @Transactional
//    void itShouldNotSaveWithWrongEmail() {
//        //Given
//        String email = "testtest.com";
//        Author testAuthor = new Author();
//        testAuthor.setName("Bob");
//        testAuthor.setEmail(email);
//        testAuthor.setBirthdate(new Date());
//        underTest.save(testAuthor);
//
//        //When
////        assertThatThrownBy(() -> underTest.save(testAuthor))
////                .isInstanceOf(ConstraintViolationException.class);
//        //Then
//        Boolean exists = underTest.existsByEmail(email);
//        assertThat(exists).isFalse();
//
//    }


    @Test
    void emailValidationTest() {
        //Given
        String email = "testtest.com";
        Author testAuthor = new Author();
        testAuthor.setEmail(email);

        // When
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Author>> violations = validator.validate(testAuthor);

        // Then
        assertThat(violations).isNotEmpty(); // Ensures that there are validation errors
        assertThat(violations).anyMatch(v -> v.getMessage().equals("Email must be in a valid format"));
    }

}