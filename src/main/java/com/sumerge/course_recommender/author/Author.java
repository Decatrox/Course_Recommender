package com.sumerge.course_recommender.author;

import com.sumerge.course_recommender.course.Course;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter @Setter
public class Author {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String email;
    private Date birthdate;

    @ManyToMany
    private List<Course> courses;

}
