package com.sumerge.course_recommender.assessment;

import com.sumerge.course_recommender.course.Course;
import jakarta.persistence.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Entity
public class Assessment {
    @Id
    @GeneratedValue
    private UUID id;

    private String content;

    @OneToOne
    @JoinColumn(name = "course_id")
    private Course course;
//    private UUID course_id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

//    public UUID getCourse_id() {
//        return course_id;
//    }
//
//    public void setCourse_id(UUID course_id) {
//        this.course_id = course_id;
//    }

    @Override
    public String toString() {
        return "Assessment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
