package com.sumerge.course_recommender.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service//@Component
public class CourseService {

    private final CourseRecommender courseRecommender;

    private final CourseRepository courseRepository;

//    @Autowired
    public CourseService(@Qualifier("courseRecommenderFun") CourseRecommender courseRecommender, CourseRepository courseRepository) {
        this.courseRecommender = courseRecommender;
        this.courseRepository = courseRepository;
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

    public Course viewCourse(UUID course_id){
        return courseRepository.findById(course_id).get();
    }

    public void deleteCourse(UUID course_id){
        courseRepository.deleteById(course_id);
    }

}
