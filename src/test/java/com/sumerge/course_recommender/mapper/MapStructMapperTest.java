package com.sumerge.course_recommender.mapper;

import com.sumerge.course_recommender.author.Author;
import com.sumerge.course_recommender.author.AuthorGetDTO;
import com.sumerge.course_recommender.course.Course;
import com.sumerge.course_recommender.course.CourseGetDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;



class MapStructMapperTest {

    private MapStructMapper underTest;

    @BeforeEach
    void setUp() {
        underTest = Mappers.getMapper(MapStructMapper.class);
    }

    @Test
    void itShouldMapCourseToCourseGetDTO() {
        //Given
        Course testCourse = new Course();
        String name = "Test Course";
        String description = "Test Description";
        int credit = 8;
        testCourse.setName(name);
        testCourse.setDescription(description);
        testCourse.setCredit(credit);

        //When
        CourseGetDTO courseGetDTO = underTest.courseToCourseGetDTO(testCourse);

        //Then
        assertThat(courseGetDTO).usingRecursiveComparison().isEqualTo(testCourse);


    }

    @Test
    void itShouldMapAuthorToAuthorGetDTO() {
        Author testAuthor = new Author();
        testAuthor.setName("Test Author");
        testAuthor.setEmail("test@gmail.com");
        testAuthor.setBirthdate(new Date());

        AuthorGetDTO authorGetDTO = underTest.authorToAuthorGetDTO(testAuthor);

        assertThat(authorGetDTO).usingRecursiveComparison().isEqualTo(testAuthor);
    }
}