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


    @GetMapping("/paged/{pageNumber}")
    public ResponseEntity<Page<CourseGetDTO>> recommendCourse(@PathVariable int pageNumber) {
        return ResponseEntity.ok(courseService.getRecommendedCourses(pageNumber));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseGetDTO> viewCourse(@PathVariable UUID id) {
        return ResponseEntity.ok(courseService.viewCourse(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCourse(@PathVariable UUID id, @Valid @RequestBody CoursePostDTO course) {
        return ResponseEntity.ok(courseService.updateCourse(id, course));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable UUID id) {
        return ResponseEntity.ok(courseService.deleteCourse(id));
    }

    @PostMapping
    public ResponseEntity<String> addCourse(@Valid @RequestBody CoursePostDTO course) {
        return ResponseEntity.ok(courseService.addCourse(course));
    }
}
