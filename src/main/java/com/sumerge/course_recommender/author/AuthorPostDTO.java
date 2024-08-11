package com.sumerge.course_recommender.author;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.util.Date;

@Data
public class AuthorPostDTO {

    @JsonProperty("name")
    @NotBlank(message = "Name is required")
    private String name;

    @JsonProperty("email")
    @Email(message = "Email must be in a valid format")
    @NotBlank(message = "Must Provide an Email")
    private String email;

    @JsonProperty("birthdate")
    @Past
    private Date birthdate;

}