package com.sumerge.course_recommender.author;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class AuthorGetDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("birthdate")
    private Date birthdate;

    @Override
    public String toString() {
        return "AuthorGetDTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birthdate=" + birthdate +
                '}';
    }
}
