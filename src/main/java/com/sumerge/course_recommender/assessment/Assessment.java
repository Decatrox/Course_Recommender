package com.sumerge.course_recommender.assessment;

import com.sumerge.course_recommender.course.Course;
import jakarta.persistence.*;
import lombok.Data;


import java.util.UUID;

@Entity
@Data
public class Assessment {
    @Id
    @GeneratedValue
    private UUID id;

    private String content;

    @OneToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
