package com.sumerge.course_recommender.course;

import com.sumerge.course_recommender.assessment.Assessment;
import com.sumerge.course_recommender.author.Author;
import com.sumerge.course_recommender.rating.Rating;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Entity
@Getter @Setter //can use constructor with the needed values only instead of Getter but here it doesn't matter I think
public class Course {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    @NotBlank(message = "Name is required")
//    will check later. need to handle when validation fails but it works.
    private String name;

    @Column
    private String description;

    @Min(value = 0, message = "Course Credit Points cannot be Negative")
    @Max(value = 12, message = "Maximum Course Credit Points is 12")
    @Column
    private int credit;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<Rating> ratings;

    @ManyToMany
    @JoinTable(
            name = "course_author"
    )
    private List<Author> authors;

    @OneToOne(cascade = CascadeType.ALL, optional = true)
    private Assessment assessment;

}
