package com.sumerge.course_recommender.course;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get courses with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course page returned successfully"),
            @ApiResponse(responseCode = "404", description = "Not enough courses in the database to reach the" +
                    " requested page number"),
    })
    @GetMapping("/paged/{pageNumber}")
    public ResponseEntity<Page<CourseGetDTO>> recommendCourse(@PathVariable int pageNumber) {
        return ResponseEntity.ok(courseService.getRecommendedCourses(pageNumber));
    }

    @Operation(summary = "View a course by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course found"),
            @ApiResponse(responseCode = "404", description = "Course not found"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<CourseGetDTO> viewCourse(@PathVariable UUID id) {
        return ResponseEntity.ok(courseService.viewCourse(id));
    }

    @Operation(summary = "Update a course by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid course data"),
            @ApiResponse(responseCode = "404", description = "Course not found"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCourse(@PathVariable UUID id, @Valid @RequestBody CoursePostDTO course) {
        return ResponseEntity.ok(courseService.updateCourse(id, course));
    }

    @Operation(summary = "Delete a course by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Course not found"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable UUID id) {
        return ResponseEntity.ok(courseService.deleteCourse(id));
    }

    @Operation(summary = "Add a new course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Course added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid course data"),
    })
    @PostMapping
    public ResponseEntity<String> addCourse(@Valid @RequestBody CoursePostDTO course) {
        return ResponseEntity.ok(courseService.addCourse(course));
    }
}
