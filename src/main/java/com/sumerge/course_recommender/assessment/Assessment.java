package com.sumerge.course_recommender.assessment;

import com.sumerge.course_recommender.course.Course;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.util.UUID;

@Entity
@ToString
public class Assessment {
    @Setter
    @Getter
    @Id
    @GeneratedValue
    private UUID id;

    @Setter
    @Getter
    private String content;

    @OneToOne
    @JoinColumn(name = "course_id")
    private Course course;

}
