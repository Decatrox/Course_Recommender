package com.sumerge.course_recommender.mapper;

import com.sumerge.course_recommender.author.Author;
import com.sumerge.course_recommender.author.AuthorGetDTO;
import com.sumerge.course_recommender.course.Course;
import com.sumerge.course_recommender.course.CourseGetDTO;
import com.sumerge.course_recommender.course.CoursePostDTO;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)

public interface MapStructMapper {
    CourseGetDTO courseToCourseGetDTO(Course course);
    Course coursePostDTOToCourse(CoursePostDTO coursePostDTO);
    AuthorGetDTO authorToAuthorGetDTO(Author author);
}
