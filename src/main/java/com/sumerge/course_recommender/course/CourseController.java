package com.sumerge.course_recommender.course;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/courses")
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;


    @GetMapping("/discover/{pageNumber}")
    public ResponseEntity<Page<Course>> recommendCourse(@PathVariable int pageNumber) {
        return ResponseEntity.ok(courseService.getRecommendedCourses(pageNumber));
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<CourseGetDTO> viewCourse(@PathVariable UUID id) {
        return ResponseEntity.ok(courseService.viewCourse(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCourse(@PathVariable UUID id, @RequestBody CoursePostDTO course) {
//        String old = courseService.viewCourse(id).toString();
        courseService.updateCourse(id, course);
        return ResponseEntity.ok("Updated Course with id: " + id + " to become\n" + course.toString());
//        return "changed " + old + " \nto " + course.toString();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable UUID id) {
//        String details = courseService.viewCourse(id).toString();
        courseService.deleteCourse(id);
        return ResponseEntity.ok("Deleted Course with id " + id);
    }

//    @Validated
    @PostMapping("/add")
    public ResponseEntity<String> addCourse(@Valid @RequestBody CoursePostDTO course) {
        courseService.addCourse(course);
        return ResponseEntity.ok("Added course: " + course.getName());
    }
}
