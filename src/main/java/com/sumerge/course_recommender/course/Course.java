package com.sumerge.course_recommender.course;

import com.sumerge.course_recommender.assessment.Assessment;
import com.sumerge.course_recommender.author.Author;
import com.sumerge.course_recommender.rating.Rating;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Entity
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Course {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String name;

    @Column
    private String description;


    @Column
    private int credit;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<Rating> ratings;

    @ManyToMany
    @JoinTable(
            name = "course_author"
    )
    private List<Author> authors;

    @OneToOne(cascade = CascadeType.ALL)
    private Assessment assessment;

}
