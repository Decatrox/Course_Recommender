package com.sumerge.course_recommender.course;

import com.sumerge.course_recommender.mapper.MapStructMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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

    public void updateCourse(UUID courseId, CoursePostDTO course){
        Course c = courseRepository.getReferenceById(courseId);
        c.setName(course.getName());
        c.setDescription(course.getDescription());
        c.setCredit(course.getCredit());
        courseRepository.save(c);
    }

    public CourseGetDTO viewCourse(UUID courseId){
        return courseRepository.findById(courseId)
                .map(mapStructMapper::courseToCourseGetDTO)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void deleteCourse(UUID courseId){
        if (!courseRepository.existsById(courseId)) throw new EntityNotFoundException();
        courseRepository.deleteById(courseId);
    }

}
