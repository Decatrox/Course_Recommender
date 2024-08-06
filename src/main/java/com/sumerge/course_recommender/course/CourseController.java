package com.sumerge.course_recommender.course;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/courses")
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;

//    public CourseController (CourseService courseService) {
//        this.courseService = courseService;
//    }

    @GetMapping("/discover/{pageNumber}")
    public ResponseEntity<Page<Course>> recommendCourse(@PathVariable int pageNumber) {
        return ResponseEntity.ok(courseService.getRecommendedCourses(pageNumber));
    }

    @GetMapping("/view")
    public ResponseEntity<CourseGetDTO> viewCourse(@RequestParam("id") UUID id) {
        return ResponseEntity.ok(courseService.viewCourse(id));
    }

    @PutMapping("/update/{id}")
    public String updateCourse(@PathVariable UUID id, @RequestBody Course course) {
        String old = courseService.viewCourse(id).toString();
        courseService.updateCourse(id, course);
        return "changed " + old + " \nto " + course.toString();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCourse(@PathVariable UUID id) {
        String details = courseService.viewCourse(id).toString();
        courseService.deleteCourse(id);
        try{
        courseService.deleteCourse(id);
        return "Deleted course: " + details;
        }
        catch (Exception e) {
            return "Could not delete course";
        }
    }

//    @Validated
    @PostMapping("/add")
    public String addCourse(@Valid @RequestBody Course course) {
        courseService.addCourse(course);
        return "Added course: " + course.getName();
//        Course savedCourse = courseService.addCourse(course);
//        return ResponseEntity.ok(savedCourse);
    }
}
