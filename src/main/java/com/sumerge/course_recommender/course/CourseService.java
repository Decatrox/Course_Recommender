package com.sumerge.course_recommender.course;

import com.sumerge.course_recommender.mapper.MapStructMapper;
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

    private final MapStructMapper mapStructMapper;


    //    @Autowired
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
//        Course cTest = courseRepository.findById(course_id).get();
//        System.out.println(cTest.getName());
//        return mapStructMapper.courseToCourseGetDTO(cTest);
        return mapStructMapper.courseToCourseGetDTO(courseRepository.findById(course_id).get());
    }

    public void deleteCourse(UUID course_id){
        courseRepository.deleteById(course_id);
    }

}
