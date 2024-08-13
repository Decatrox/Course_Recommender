package com.sumerge.course_recommender.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Table
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class AppUser implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String userName;

    @Column(length = 60)
    private String password;
}
