package com.sumerge.course_recommender;

import com.sumerge.course_recommender.model.Course;
import com.sumerge.course_recommender.repo.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service//@Component
public class CourseService {

    CourseRecommender courseRecommender;
    @Autowired
    private CourseRecommender courseRecommenderFun;
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    public CourseService(@Qualifier("courseRecommenderFun") CourseRecommender courseRecommender) {
        this.courseRecommender = courseRecommender;
    }

    @Autowired
    public void setCourseRecommender(CourseRecommender courseRecommender) {
        this.courseRecommender = courseRecommender;
    }

    List<Course_old> getRecommendedCourses() {
//        return courseRecommender.recommendedCourses();
        return courseRecommenderFun.recommendedCourses();
    }

    public void addCourse(Course course){
        courseRepository.addCourse(course);
    }

    public void updateCourse(UUID course_id, Course course){
        courseRepository.updateCourse(course_id, course);
    }

    public String viewCourse(UUID course_id){
        return courseRepository.viewCourse(course_id);
    }

    public void deleteCourse(UUID course_id){
        courseRepository.deleteCourse(course_id);
    }

}
