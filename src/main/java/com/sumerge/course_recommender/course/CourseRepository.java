package com.sumerge.course_recommender.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID>, ListPagingAndSortingRepository<Course, UUID> {
}
