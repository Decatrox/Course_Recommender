package com.sumerge.course_recommender.controller;

import com.sumerge.course_recommender.CourseService;
import com.sumerge.course_recommender.Course_old;
import com.sumerge.course_recommender.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/courses")
public class CourseController {

//    @Autowired
    private CourseService courseService;

    @Autowired
    public CourseController (CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/discover")
    public ResponseEntity<List<Course>> recommendCourse() {
        return ResponseEntity.ok(courseService.getRecommendedCourses());
    }

    @GetMapping("/view")
    public ResponseEntity<Course> viewCourse(@RequestParam("id") UUID id) {
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
        try{
        courseService.deleteCourse(id);
        return "Deleted course: " + details;
        }
        catch (Exception e) {
            return "Could not delete course";
        }
    }


    @PostMapping("/add")
    public String addCourse(@RequestBody Course course) {
        courseService.addCourse(course);
        return "Added course: " + course.getName();
    }
}
