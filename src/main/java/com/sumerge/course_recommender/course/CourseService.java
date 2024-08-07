package com.sumerge.course_recommender.course;

import com.sumerge.course_recommender.course.recommenders.CourseRecommenderFun;
import com.sumerge.course_recommender.mapper.MapStructMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service//@Component
@Transactional
@AllArgsConstructor
public class CourseService {

    private final CourseRecommender courseRecommender;
    private final CourseRepository courseRepository;
    private final MapStructMapper mapStructMapper;


    public Page<Course> getRecommendedCourses(int pageNumber) {
        return courseRecommender.recommendedCourses(pageNumber);
    }

    public void addCourse(CoursePostDTO course){
        courseRepository.save(mapStructMapper.coursePostDTOToCourse(course));
    }

    public void updateCourse(UUID course_id, CoursePostDTO course){
        Course c = courseRepository.getReferenceById(course_id);
        c.setName(course.getName());
        c.setDescription(course.getDescription());
        c.setCredit(course.getCredit());
        courseRepository.save(c);
    }

    public CourseGetDTO viewCourse(UUID course_id){
        return mapStructMapper.courseToCourseGetDTO(courseRepository.findById(course_id).get());
    }

    public void deleteCourse(UUID course_id){
            courseRepository.deleteById(course_id);
    }

}
