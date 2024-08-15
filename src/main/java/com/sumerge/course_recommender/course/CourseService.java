package com.sumerge.course_recommender.course;

import com.sumerge.course_recommender.exception_handling.custom_exceptions.CourseAlreadyExistsException;
import com.sumerge.course_recommender.exception_handling.custom_exceptions.CourseNotFoundException;
import com.sumerge.course_recommender.exception_handling.custom_exceptions.PageNotFoundException;
import com.sumerge.course_recommender.mapper.MapStructMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service//@Component
@Transactional
@AllArgsConstructor
@Slf4j
public class CourseService {

    private final CourseRecommender courseRecommender;
    private final CourseRepository courseRepository;
    private final MapStructMapper mapStructMapper;
    private static final String COURSE_WITH_ID = "Course with id: ";
    private static final String DOES_NOT_EXIST = " does not exist";


    public Page<CourseGetDTO> getRecommendedCourses(int pageNumber) {
        log.info("CourseService-Get recommended courses");
        Page<CourseGetDTO> page = mapStructMapper.pageCourseToPageCourseGetDTO(courseRecommender.getRecommendedCoursesPage(pageNumber));

        if (!page.hasContent()){
            throw new PageNotFoundException("Page: " + pageNumber + " Not Found");
        }
        return page;
    }

    public String addCourse(CoursePostDTO course){
        log.info("CourseService-Add recommended course");
        if (courseRepository.existsByName(course.getName())) {
            throw new CourseAlreadyExistsException("Course with the name: " + course.getName() + " already exists");
        }
        courseRepository.save(mapStructMapper.coursePostDTOToCourse(course));
        return "Added course: " + course.getName();
    }

    public String updateCourse(UUID courseId, CoursePostDTO course){
        log.info("CourseService-Update recommended course");
        if (!courseRepository.existsById(courseId)) {
            throw new CourseNotFoundException(COURSE_WITH_ID + courseId + DOES_NOT_EXIST);
        }
        if (courseRepository.existsByName(course.getName())
                && !courseRepository.getReferenceById(courseId).getName().equals(course.getName())) {
            throw new CourseAlreadyExistsException("Course with the name: " + course.getName() + " already exists");
        }
        Course c = courseRepository.getReferenceById(courseId);
        c.setName(course.getName());
        c.setDescription(course.getDescription());
        c.setCredit(course.getCredit());
        courseRepository.save(c);
        return "Updated " + COURSE_WITH_ID + courseId + " to become\n" + course;
    }

    public CourseGetDTO viewCourse(UUID courseId){
        log.info("CourseService-View recommended course");
        return courseRepository.findById(courseId)
                .map(mapStructMapper::courseToCourseGetDTO)
                .orElseThrow(() -> new CourseNotFoundException(COURSE_WITH_ID + courseId + DOES_NOT_EXIST));
    }

    public String deleteCourse(UUID courseId){
        log.info("CourseService-Delete recommended course");
        if (!courseRepository.existsById(courseId)) throw new CourseNotFoundException(COURSE_WITH_ID + courseId + DOES_NOT_EXIST);
        courseRepository.deleteById(courseId);
        return "Deleted " + COURSE_WITH_ID + courseId;
    }
}
