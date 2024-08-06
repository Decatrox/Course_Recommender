package com.sumerge.course_recommender.course;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CourseGetDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("credit")
    private int credit;

    @Override
    public String toString() {
        return "CourseGetDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", credit=" + credit +
                '}';
    }
}
