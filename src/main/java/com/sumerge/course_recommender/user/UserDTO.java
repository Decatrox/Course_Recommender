package com.sumerge.course_recommender.user;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserDTO {
    @Column
    private String userName;

    @Column
    private String password;
}
