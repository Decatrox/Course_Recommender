package com.sumerge.course_recommender.rating;

import com.sumerge.course_recommender.course.Course;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
public class Rating {
    @Id
    @Getter
    @Setter
    @GeneratedValue
    private UUID id;

    @Getter @Setter
    private int number;

    @ManyToOne
    private Course course;

}
