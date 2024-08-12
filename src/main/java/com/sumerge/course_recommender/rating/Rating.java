package com.sumerge.course_recommender.rating;

import com.sumerge.course_recommender.course.Course;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Rating {
    @Id
    @GeneratedValue
    private UUID id;

    private int number;

    @ManyToOne
    private Course course;
}
