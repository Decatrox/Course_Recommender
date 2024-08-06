package com.sumerge.course_recommender.course;

import com.sumerge.course_recommender.mapper.MapStructMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service//@Component
@Transactional
public class CourseService {

    private final CourseRecommender courseRecommender;

    private final CourseRepository courseRepository;

    private final MapStructMapper mapStructMapper;


    public CourseService(@Qualifier("courseRecommenderFun") CourseRecommender courseRecommender, CourseRepository courseRepository, MapStructMapper mapStructMapper) {
        this.courseRecommender = courseRecommender;
        this.courseRepository = courseRepository;
        this.mapStructMapper = mapStructMapper;
    }

    public Page<Course> getRecommendedCourses(int pageNumber) {

        return courseRecommender.recommendedCourses(pageNumber);
    }

    public void addCourse(Course course){
        courseRepository.save(course);
    }

    public void updateCourse(UUID course_id, Course course){
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
        if (courseRepository.existsById(course_id)){
            courseRepository.deleteById(course_id);
        }
//        else{
//            throw
//        }
    }

}
