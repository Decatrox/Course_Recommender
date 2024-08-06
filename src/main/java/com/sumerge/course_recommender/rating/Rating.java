package com.sumerge.course_recommender.rating;

import com.sumerge.course_recommender.course.Course;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Entity
public class Rating {
    @Id
    @GeneratedValue
    private UUID id;

    private int number;

    @ManyToOne
    private Course course;
//    private UUID course_id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

//    public UUID getCourse_id() {
//        return course_id;
//    }
//
//    public void setCourse_id(UUID course_id) {
//        this.course_id = course_id;
//    }
}
