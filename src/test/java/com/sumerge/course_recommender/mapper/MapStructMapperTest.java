package com.sumerge.course_recommender.mapper;

import com.sumerge.course_recommender.author.Author;
import com.sumerge.course_recommender.author.AuthorGetDTO;
import com.sumerge.course_recommender.author.AuthorPostDTO;
import com.sumerge.course_recommender.course.Course;
import com.sumerge.course_recommender.course.CourseGetDTO;
import com.sumerge.course_recommender.course.CoursePostDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Date;

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

        testCourse = null;
        courseGetDTO = underTest.courseToCourseGetDTO(testCourse);
        assertThat(courseGetDTO).isNull();

    }

    @Test
    void itShouldMapCoursePostDTOToCourse() {
        //Given
        CoursePostDTO testCourse = new CoursePostDTO();
        String name = "Test Course";
        String description = "Test Description";
        int credit = 8;
        testCourse.setName(name);
        testCourse.setDescription(description);
        testCourse.setCredit(credit);

        //When
        Course course = underTest.coursePostDTOToCourse(testCourse);

        //Then
        assertThat(testCourse).usingRecursiveComparison().ignoringFields("id").isEqualTo(course);

        //check null
        testCourse = null;
        course = underTest.coursePostDTOToCourse(testCourse);
        assertThat(course).isNull();

    }

    @Test
    void itShouldMapAuthorToAuthorGetDTO() {
        Author testAuthor = new Author();
        testAuthor.setName("Test Author");
        testAuthor.setEmail("test@gmail.com");
        testAuthor.setBirthdate(new Date());

        AuthorGetDTO authorGetDTO = underTest.authorToAuthorGetDTO(testAuthor);

        assertThat(authorGetDTO).usingRecursiveComparison().isEqualTo(testAuthor);

        //test null
        testAuthor = null;
        authorGetDTO = underTest.authorToAuthorGetDTO(testAuthor);
        assertThat(authorGetDTO).isNull();
    }

    @Test
    void itShouldMapAuthorPostDTOToAuthor() {
        AuthorPostDTO testAuthor = new AuthorPostDTO();
        testAuthor.setName("Test Author");
        testAuthor.setEmail("test@gmail.com");
        testAuthor.setBirthdate(new Date());

        Author author = underTest.authorPostDTOToAuthor(testAuthor);

        assertThat(testAuthor).usingRecursiveComparison().isEqualTo(author);

        //test null
        testAuthor = null;
        author = underTest.authorPostDTOToAuthor(testAuthor);
        assertThat(author).isNull();
    }
}