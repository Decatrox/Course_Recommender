package com.sumerge.course_recommender.course;

import com.sumerge.course_recommender.exception_handling.CourseAlreadyExistsException;
import com.sumerge.course_recommender.exception_handling.CourseNotFoundException;
import com.sumerge.course_recommender.exception_handling.PageNotFoundException;
import com.sumerge.course_recommender.mapper.MapStructMapper;
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


    public Page<CourseGetDTO> getRecommendedCourses(int pageNumber) {
        Page<CourseGetDTO> page = mapStructMapper.pageCourseToPageCourseGetDTO(courseRecommender.recommendedCourses(pageNumber));

        if (!page.hasContent()){
            throw new PageNotFoundException("Page: " + pageNumber + " Not Found");
        }
        return page;
    }

    public String addCourse(CoursePostDTO course){
        if (courseRepository.existsByName(course.getName())) {
            throw new CourseAlreadyExistsException("Course with the name: " + course.getName() + " already exists");
        }
        courseRepository.save(mapStructMapper.coursePostDTOToCourse(course));
        return "Added course: " + course.getName();
    }

    public String updateCourse(UUID courseId, CoursePostDTO course){
        if (!courseRepository.existsById(courseId)) {
            throw new CourseNotFoundException("Course with id: " + courseId + " does not exist");
        }
        Course c = courseRepository.getReferenceById(courseId);
        c.setName(course.getName());
        c.setDescription(course.getDescription());
        c.setCredit(course.getCredit());
        courseRepository.save(c);
        return "Updated Course with id: " + courseId + " to become\n" + course;
    }

    public CourseGetDTO viewCourse(UUID courseId){
        return courseRepository.findById(courseId)
                .map(mapStructMapper::courseToCourseGetDTO)
                .orElseThrow(() -> new CourseNotFoundException("Course with id: " + courseId + " does not exist"));
    }

    public String deleteCourse(UUID courseId){
        if (!courseRepository.existsById(courseId)) throw new CourseNotFoundException("Course with id: " + courseId + " does not exist");
        courseRepository.deleteById(courseId);
        return "Deleted Course with id " + courseId;
    }
}
